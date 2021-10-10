import threading


def any_function():
    print("timeout message\n")


# first way
timer = threading.Timer(2.0, any_function).start()

# second way
# timer = threading.Timer(2.0, any_function)
# timer.start()
print("Exit\n")
