#include "skip_list.h"

#include <stdlib.h>

#include "../../Resources/C/Unity/unity.h"

// Data elements that are initialized before each test
static int i1, i2, i3, i4, i5;
static SkipList* skip_list_int;

int compare(void* v1, void* v2) {
    if (v1 == NULL) {
        printf("compare(v1, v2): v1 must not be NULL\n");
        exit(EXIT_FAILURE);
    }
    if (v2 == NULL) {
        printf("compare(v1, v2): v2 must not be NULL\n");
        exit(EXIT_FAILURE);
    }
    int i1 = *((int*)(v1));
    int i2 = *((int*)(v2));
    return i1 - i2;
}

// Function called on test to initialize the environment
void setUp(void) {
    i1 = 53;
    i2 = 47;
    i3 = 91;
    i4 = 14;
    i5 = 7;
    skip_list_int = createSkipList(compare);
}

static void test_skip_list_insert_one(void) {
    TEST_ASSERT_EQUAL_INT(1, insertSkpiList(skip_list_int, &i1));
}

static void test_skip_list_insert_two(void) {
    int ris = insertSkpiList(skip_list_int, &i1);
    ris += insertSkpiList(skip_list_int, &i2);
    TEST_ASSERT_EQUAL_INT(2, ris);
}

static void test_skip_list_search_one(void) {
    insertSkpiList(skip_list_int, &i1);
    TEST_ASSERT_EQUAL_INT(1, searchSkipList(skip_list_int, &i1));
}

// Function called on test to clear the environment
void tearDown(void) {
    freeSkipList(skip_list_int, NULL);
}

int main(void) {
    UNITY_BEGIN();

    RUN_TEST(test_skip_list_insert_one);
    RUN_TEST(test_skip_list_insert_two);
    RUN_TEST(test_skip_list_search_one);

    return UNITY_END();
}