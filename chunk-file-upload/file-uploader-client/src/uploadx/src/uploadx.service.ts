import { Injectable } from '@angular/core';

import { Subject, Observable } from 'rxjs';

import {
  UploadxOptions,
  UploadState,
  UploadxControlEvent,
  UploaderOptions,
  UploadStatus
} from './interfaces';
import { Uploader } from './uploader';

/**
 *
 */
@Injectable({ providedIn: 'root' })
export class UploadxService {
  subj: Subject<UploadState> = new Subject();
  queue: Uploader[] = [];
  private concurrency = 2;
  private autoUpload = true;
  private options: UploadxOptions;

  get uploaderOptions(): UploaderOptions {
    return {
      method: this.options.method || 'POST',
      // tslint:disable-next-line: deprecation
      endpoint: this.options.endpoint || this.options.url || '/upload/',
      headers: this.options.headers,
      token: this.options.token,
      chunkSize: this.options.chunkSize || 0,
      withCredentials: this.options.withCredentials || false,
      subj: this.subj
    } as UploaderOptions;
  }

  constructor() {
    this.subj.subscribe((uploadState: UploadState) => {
      if (uploadState.status !== 'uploading') {
        this.processQueue();
      }
    });
  }
  /**
   * Set global options
   */
  init(options: UploadxOptions): Observable<UploadState> {
    this.queue = [];
    this.options = options;
    this.concurrency = options.concurrency || this.concurrency;
    this.autoUpload = !(options.autoUpload === false);
    return this.subj.asObservable();
  }
  /**
   *
   * Create Uploader and add to the queue
   */
  handleFileList(fileList: FileList) {
    for (let i = 0; i < fileList.length; i++) {
      const uploader: Uploader = new Uploader(fileList.item(i), this.uploaderOptions);
      this.queue.push(uploader);
      uploader.status = 'added';
    }
    this.autoUploadFiles();
  }
  /**
   *
   * Create Uploader for the file and add to the queue
   */
  handleFile(file: File) {
    const uploader: Uploader = new Uploader(file, this.uploaderOptions);
    this.queue.push(uploader);
    uploader.status = 'added';
    this.autoUploadFiles();
  }
  /**
   *
   * Auto upload the files if the flag is true
   */
  private autoUploadFiles() {
    if (this.autoUpload) {
      this.queue.filter(f => f.status === 'added').forEach(f => (f.status = 'queue'));
    }
  }
  /**
   * Control uploads status
   * @example
   * this.uploadService.control({ action: 'pauseAll' });
   *
   */
  control(event: UploadxControlEvent) {
    switch (event.action) {
      case 'cancelAll':
        this.queue.forEach(f => (f.status = 'cancelled'));
        break;
      case 'pauseAll':
        this.queue.forEach(f => (f.status = 'paused'));
        break;
      case 'refreshToken':
        this.queue.forEach(f => (f.options.token = event.token));
        break;
      case 'uploadAll':
        this.queue.filter(f => f.status !== 'uploading').forEach(f => (f.status = 'queue'));
        break;
      case 'upload':
        const uploadId = event.uploadId || event.itemOptions.uploadId;
        const upload = this.queue.find(f => f.uploadId === uploadId);
        upload.configure(event.itemOptions);
        upload.status = 'queue' as UploadStatus;
        break;
      case 'cancel':
        this.queue.find(f => f.uploadId === event.uploadId).status = 'cancelled';
        break;
      case 'pause':
        this.queue.find(f => f.uploadId === event.uploadId).status = 'paused';
        break;
      default:
        break;
    }
  }

  /**
   * Queue management
   */
  private processQueue() {
    const running = this.runningProcess();

    this.queue
      .filter((uploader: Uploader) => uploader.status === 'queue')
      .slice(0, Math.max(this.concurrency - running, 0))
      .forEach((uploader: Uploader) => {
        uploader.upload();
      });
  }

  runningProcess(): number {
    return this.queue.filter((uploader: Uploader) => uploader.status === 'uploading').length;
  }
}
