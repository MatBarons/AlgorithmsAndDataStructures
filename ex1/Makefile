CC = gcc

all: tests ex1

tests: bin/generic_array_tests bin/sorting_tests
	bin/generic_array_tests
	bin/sorting_tests

ex1: bin/ex1
	bin/ex1 "../Resources/Datasets/records.csv" 

CFLAGS = -g -O3 -Wall -Wextra -Wpedantic -Wconversion

INCLUDES = src/*.h

COMMON_DEPS = $(INCLUDES) Makefile

build/unity.o: ../Resources/C/Unity/unity.c ../Resources/C/Unity/unity.h ../Resources/C/Unity/unity_internals.h
	$(CC) $(CFLAGS) -c ../Resources/C/Unity/unity.c -o build/unity.o

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

bin/generic_array_tests: build/unity.o build/generic_array_tests.o build/generic_array.o $(COMMON_DEPS)
	$(CC) -o bin/generic_array_tests build/generic_array_tests.o build/generic_array.o build/unity.o

bin/sorting_tests: build/unity.o build/sorting_tests.o build/sorting.o build/generic_array.o $(COMMON_DEPS)
	$(CC) -o bin/sorting_tests build/sorting_tests.o build/sorting.o build/unity.o build/generic_array.o

bin/ex1: build/ex1.o build/sorting.o build/generic_array.o $(COMMON_DEPS)
	$(CC) -o bin/ex1 build/ex1.o build/sorting.o build/generic_array.o

clean:
	rm -f build/* bin/*