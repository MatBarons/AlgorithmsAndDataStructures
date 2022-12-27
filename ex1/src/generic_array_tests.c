#include <stdlib.h>

#include "../../Resources/C/Unity/unity.h"
#include "generic_array.h"

// Data elements that are initialized before each test
static int i1, i2, i3, i4, i5, i6, i7, i8, i9, i10;
static GenericArray* generic_array_int;

// Function called on test to initialize the environment
void setUp(void) {
    i1 = 53;
    i2 = 47;
    i3 = 91;
    i4 = 14;
    i5 = 7;
    i6 = 9;
    i7 = 7;
    i8 = 41;
    i9 = 76;
    i10 = 0;
    generic_array_int = generic_array_create();
}

// Function called on test to clear the environment
void tearDown(void) {
    generic_array_destroy(generic_array_int);
}

// TEST ERROR
static void test_generic_array_null_size(void) {
    TEST_ASSERT_EQUAL_INT(0, generic_array_size(NULL));
}

static void test_generic_array_null_insert_one_el(void) {
    TEST_ASSERT_NULL(generic_array_insert(NULL, &i1));
}

static void test_generic_array_insert_null(void) {
    TEST_ASSERT_NULL(generic_array_insert(generic_array_int, NULL));
}

static void test_generic_array_one_el_update_index_error(void) {
    generic_array_insert(generic_array_int, &i1);
    TEST_ASSERT_NULL(generic_array_update_at(generic_array_int, &i3, 2));
}

static void test_generic_array_one_el_get_index_error(void) {
    generic_array_insert(generic_array_int, &i1);
    TEST_ASSERT_NULL(generic_array_get(generic_array_int, 3));
}

static void test_generic_array_null_clear(void) {
    TEST_ASSERT_EQUAL_INT(-1, generic_array_clear(NULL));
}

static void test_generic_array_null_destroy(void) {
    TEST_ASSERT_EQUAL_INT(-1, generic_array_destroy(NULL));
}

// TEST INSERT & SIZE
static void test_generic_array_size_zero_el(void) {
    TEST_ASSERT_EQUAL_INT(0, generic_array_size(generic_array_int));
}

static void test_generic_array_size_one_el(void) {
    generic_array_insert(generic_array_int, &i1);
    TEST_ASSERT_EQUAL_INT(1, generic_array_size(generic_array_int));
}

static void test_generic_array_size_eleven_el(void) {
    generic_array_insert(generic_array_int, &i1);
    generic_array_insert(generic_array_int, &i2);
    generic_array_insert(generic_array_int, &i3);
    generic_array_insert(generic_array_int, &i4);
    generic_array_insert(generic_array_int, &i5);
    generic_array_insert(generic_array_int, &i6);
    generic_array_insert(generic_array_int, &i7);
    generic_array_insert(generic_array_int, &i8);
    generic_array_insert(generic_array_int, &i9);
    generic_array_insert(generic_array_int, &i10);
    generic_array_insert(generic_array_int, &i3);
    TEST_ASSERT_EQUAL_INT(11, generic_array_size(generic_array_int));
}

// TEST INSERT
static void test_generic_array_insert_one_el(void) {
    TEST_ASSERT_EQUAL_PTR(&i1, generic_array_insert(generic_array_int, &i1));
}

static void test_generic_array_insert_ten_el(void) {
    int* exp_arr[] = {&i1, &i2, &i3, &i4, &i5, &i6, &i7, &i8, &i9, &i10};
    int** act_arr = malloc(10 * sizeof(int*));
    for (int i = 0; i < 10; ++i) {
        act_arr[i] = (int*)generic_array_insert(generic_array_int, exp_arr[i]);
    }
    TEST_ASSERT_EQUAL_PTR_ARRAY(act_arr, exp_arr, 10);
}

// TEST UPDATE
static void test_generic_array_two_el_update_first(void) {
    generic_array_insert(generic_array_int, &i1);
    generic_array_insert(generic_array_int, &i2);
    TEST_ASSERT_EQUAL_PTR(&i3, generic_array_update_at(generic_array_int, &i3, 0));
}

static void test_generic_array_two_el_update_both(void) {
    int* exp_arr[] = {&i3, &i1};
    int** act_arr = malloc(2 * sizeof(int*));
    generic_array_insert(generic_array_int, &i1);
    generic_array_insert(generic_array_int, &i2);
    act_arr[0] = (int*)generic_array_update_at(generic_array_int, &i3, 0);
    act_arr[1] = (int*)generic_array_update_at(generic_array_int, &i1, 1);
    TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 2);
}

// TEST INSERT & GET
static void test_generic_array_insert_one_get_first_el(void) {
    generic_array_insert(generic_array_int, &i1);
    TEST_ASSERT_EQUAL_PTR(&i1, generic_array_get(generic_array_int, 0));
}

static void test_generic_array_insert_two_get_two_el(void) {
    int* exp_arr[] = {&i1, &i3};
    generic_array_insert(generic_array_int, &i1);
    generic_array_insert(generic_array_int, &i3);
    int** act_arr = malloc(2 * sizeof(int*));
    act_arr[0] = (int*)generic_array_get(generic_array_int, 0);
    act_arr[1] = (int*)generic_array_get(generic_array_int, 1);
    TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 2);
}

static void test_generic_array_insert_three_get_last_el(void) {
    generic_array_insert(generic_array_int, &i1);
    generic_array_insert(generic_array_int, &i3);
    generic_array_insert(generic_array_int, &i2);
    TEST_ASSERT_EQUAL_PTR(&i2, generic_array_get(generic_array_int, 2));
}

// TEST CLEAR
static void test_generic_array_clear(void) {
    int* el1 = (int*)malloc(sizeof(int));
    int* el2 = (int*)malloc(sizeof(int));

    *el1 = 2;
    *el2 = 6;

    generic_array_insert(generic_array_int, el1);
    generic_array_insert(generic_array_int, el2);
    generic_array_clear(generic_array_int);

    TEST_ASSERT_EQUAL(0, generic_array_size(generic_array_int));
}

int main(void) {
    UNITY_BEGIN();

    // TEST ERROR
    RUN_TEST(test_generic_array_null_size);
    RUN_TEST(test_generic_array_null_insert_one_el);
    RUN_TEST(test_generic_array_insert_null);
    RUN_TEST(test_generic_array_one_el_update_index_error);
    RUN_TEST(test_generic_array_one_el_get_index_error);
    RUN_TEST(test_generic_array_null_clear);
    RUN_TEST(test_generic_array_null_destroy);

    // TEST SIZE
    RUN_TEST(test_generic_array_size_zero_el);
    RUN_TEST(test_generic_array_size_one_el);
    RUN_TEST(test_generic_array_size_eleven_el);

    // TEST INSERT
    RUN_TEST(test_generic_array_insert_one_el);
    RUN_TEST(test_generic_array_insert_ten_el);

    // TEST UPDATE
    RUN_TEST(test_generic_array_two_el_update_first);
    RUN_TEST(test_generic_array_two_el_update_both);

    // TEST INSERT & GET
    RUN_TEST(test_generic_array_insert_one_get_first_el);
    RUN_TEST(test_generic_array_insert_two_get_two_el);
    RUN_TEST(test_generic_array_insert_three_get_last_el);

    // TEST CLEAR
    RUN_TEST(test_generic_array_clear);

    return UNITY_END();
}