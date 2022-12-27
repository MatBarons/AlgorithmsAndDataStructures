RED=\e[1;31m
GREEN=\e[1;32m
YELLOW=\e[1;33m
BLUE=\e[1;34m
CYAN=\e[1;36m
MAGENTA=\e[1;35m
BOLD=\e[1m
NC=\e[0m

CC = gcc

all: tests ex2
# all: ex2

help:
	@echo "$(BOLD)~~~ $(CYAN)HELP$(NC)$(BOLD) ~~~$(NC)"
	@echo "\tUse $(BOLD)«make»$(NC) or $(BOLD)«make all»$(NC) to compile and launch $(YELLOW)main_ex2$(NC) and $(YELLOW)tests$(NC)"
	@echo "\tUse $(BOLD)«make tests»$(NC) to compile and launch $(YELLOW)tests$(NC)"
	@echo "\tUse $(BOLD)«make ex2»$(NC) to compile and launch $(YELLOW)main_ex2$(NC)"
	@echo "\tUse $(BOLD)«make clean»$(NC) to clean $(MAGENTA)obj$(NC) and $(MAGENTA)build$(NC) directory"

tests: build/skip_list_test
	build/skip_list_test

ex2: build/main_ex2
	build/main_ex2 "../Resources/Datasets/dictionary.txt" "../Resources/Datasets/correctme.txt" 

CFLAGS = -g -Wall -Wextra -Wpedantic -Wconversion

INCLUDES = src/*.h

COMMON_DEPS = $(INCLUDES) Makefile

obj/unity.o: ../Resources/C/Unity/unity.c ../Resources/C/Unity/unity.h ../Resources/C/Unity/unity_internals.h
	$(CC) $(CFLAGS) -c ../Resources/C/Unity/unity.c -o obj/unity.o

obj/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

build/skip_list_test: obj/unity.o obj/skip_list_test.o obj/skip_list.o $(COMMON_DEPS)
	$(CC) -o build/skip_list_test obj/skip_list_test.o obj/skip_list.o obj/unity.o

build/main_ex2: obj/main_ex2.o obj/skip_list.o $(COMMON_DEPS)
	$(CC) -o build/main_ex2 obj/main_ex2.o obj/skip_list.o

clean:
	rm -f obj/* build/*