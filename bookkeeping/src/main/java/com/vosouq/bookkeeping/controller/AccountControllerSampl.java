//package com.vosouq.bookkeeping.controller;
//
//
//import com.vosouq.commons.annotation.Created;
//import com.vosouq.commons.annotation.NoContent;
//import com.vosouq.commons.annotation.VosouqRestController;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@VosouqRestController
//@RequestMapping(value = "/accounts")
//public class AccountController {
//
//    @PostMapping
//    @Created
//    public Long create(@RequestBody Object o) {
//        return null;
//    }
//
//    @GetMapping
//    public List<Object> getAll() {
//        return null;
//    }
//
//    @GetMapping("/{id}")
//    public Object get(@PathVariable long id) {
//        return null;
//    }
//
//    @PutMapping("{id}")
//    @NoContent
//    public void update(@PathVariable long id, @RequestBody Object o) {
//    }
//
//    @DeleteMapping("{id}")
//    @NoContent
//    public void delete(@PathVariable long id) {
//    }
//
//
//}
