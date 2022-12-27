#include <stdlib.h>

#include "../../Resources/C/Unity/unity.h"
#include "sorting.h"

// Data elements that are initialized before each test
static int i1, i2, i3, i4, i5, i6, i7, i8, i9, i10;
static GenericArray* generic_array_int;

static int compare_int(void* i1_p, void* i2_p) {
    int* int1_p = (int*)i1_p;
    int* int2_p = (int*)i2_p;

    if ((*int1_p) > (*int2_p))
        return (1);
    else if ((*int1_p) < (*int2_p))
        return (-1);
    else
        return (0);
}

// Function called on test to initialize the environment
void setUp(void) {
    i1 = 233460;
    i2 = 4741192;
    i3 = 1014671;
    i4 = 496325;
    i5 = 4476757;
    i6 = 3754104;
    i7 = 4271997;
    i8 = 4896376;
    i9 = 2735414;
    i10 = 2163766;
    generic_array_int = generic_array_create();
}

// Function called on test to clear the environment
void tearDown(void) {
    generic_array_destroy(generic_array_int);
}

// TEST ERROR
static void test_array_null_swap(void) {
    TEST_ASSERT_EQUAL_INT(-1, swap(NULL, 0, 1));
}

static void test_array_one_el_swap_two_el(void) {
    generic_array_insert(generic_array_int, &i4);
    TEST_ASSERT_EQUAL_INT(-1, swap(generic_array_int, 0, 1));
}

// TEST SWAP
static void test_array_two_el_swap_two_el(void) {
    int* exp_arr[] = {&i3, &i1};
    int** act_arr = malloc(2 * sizeof(int*));
    generic_array_insert(generic_array_int, &i1);
    generic_array_insert(generic_array_int, &i3);
    swap(generic_array_int, 0, 1);
    act_arr[0] = (int*)generic_array_get(generic_array_int, 0);
    act_arr[1] = (int*)generic_array_get(generic_array_int, 1);
    TEST_ASSERT_EQUAL_PTR_ARRAY(act_arr, exp_arr, 2);
}

static void test_array_ten_el_swap_first_last(void) {
    int* exp_arr[] = {&i10, &i2, &i3, &i4, &i5, &i6, &i7, &i8, &i9, &i1};

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

    swap(generic_array_int, 0, 9);

    int** act_arr = malloc(10 * sizeof(int*));
    for (unsigned long i = 0; i < 10; ++i) {
        act_arr[i] = (int*)generic_array_get(generic_array_int, i);
    }
    TEST_ASSERT_EQUAL_PTR_ARRAY(act_arr, exp_arr, 10);
}

// TEST QUICKSORT
static void test_quick_sort_array_ten_el(void) {
    int* exp_arr[] = {&i1, &i4, &i3, &i10, &i9, &i6, &i7, &i5, &i2, &i8};

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

    quick_sort(generic_array_int, compare_int);

    int** act_arr = malloc(10 * sizeof(int*));

    for (unsigned long i = 0; i < 10; ++i) {
        act_arr[i] = (int*)generic_array_get(generic_array_int, i);
    }

    TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 10);
}

static void test_quick_sort_array_one_el(void) {
    int* exp_arr[] = {&i7};

    generic_array_insert(generic_array_int, &i7);
    quick_sort(generic_array_int, compare_int);

    int** act_arr = malloc(1 * sizeof(int*));

    act_arr[0] = generic_array_get(generic_array_int, 0);

    TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 1);
}

// TEST BINARY_INSERTION_SORT
static void test_binary_insertion_sort_array_ten_el(void) {
    int* exp_arr[] = {&i1, &i4, &i3, &i10, &i9, &i6, &i7, &i5, &i2, &i8};

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

    generic_array_int = binary_insertion_sort(generic_array_int, compare_int);

    int** act_arr = malloc(10 * sizeof(int*));

    for (unsigned long i = 0; i < 10; ++i) {
        act_arr[i] = (int*)generic_array_get(generic_array_int, i);
    }

    TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 10);
}

int main(void) {
    UNITY_BEGIN();

    // TEST ERROR
    RUN_TEST(test_array_null_swap);
    RUN_TEST(test_array_one_el_swap_two_el);

    // TEST SWAP
    RUN_TEST(test_array_two_el_swap_two_el);
    RUN_TEST(test_array_ten_el_swap_first_last);

    // TEST QUICKSORT
    RUN_TEST(test_quick_sort_array_ten_el);
    RUN_TEST(test_quick_sort_array_one_el);

    // TEST BINARY_INSERTION_SORT
    RUN_TEST(test_binary_insertion_sort_array_ten_el);

    return UNITY_END();
}