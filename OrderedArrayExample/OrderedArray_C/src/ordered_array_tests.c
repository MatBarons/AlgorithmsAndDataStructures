#include <stdio.h>
#include <stdlib.h>
#include "unity.h"
#include "ordered_array.h"

/*
 * Test suite for ordered array data structure and algorithms
 */

//precedence relation used in tests
static int precedes_int(void* i1_p,void* i2_p){
  int* int1_p = (int*)i1_p;
  int* int2_p = (int*)i2_p;
  if((*int1_p) < (*int2_p))
    return(1);
  return(0);
}



//Data elements that are initialized before each test
static int i1,i2,i3;
static OrderedArray *ordered_array_int;

void setUp(void){
  i1 = -12;
  i2 = 0;
  i3 = 4;
  ordered_array_int = ordered_array_create(precedes_int);
}



void tearDown(void){
  ordered_array_free_memory(ordered_array_int);
}


static void test_ordered_array_is_empty_zero_el(void){
  TEST_ASSERT_TRUE(ordered_array_is_empty(ordered_array_int));
}



static void test_ordered_array_is_empty_one_el(void){
 ordered_array_add(ordered_array_int,&i1);
 TEST_ASSERT_FALSE(ordered_array_is_empty(ordered_array_int));
}
  
  
static void test_ordered_array_size_zero_el(void){
  TEST_ASSERT_EQUAL_INT(0,ordered_array_size(ordered_array_int));
}

  
static void test_ordered_array_size_one_el(void){
  ordered_array_add(ordered_array_int,&i1);
  TEST_ASSERT_EQUAL_INT(1,ordered_array_size(ordered_array_int));
}

  
static void test_ordered_array_size_two_el(void){
  ordered_array_add(ordered_array_int,&i1);
  ordered_array_add(ordered_array_int,&i2);
  TEST_ASSERT_EQUAL_INT(2,ordered_array_size(ordered_array_int));
}

  
static void test_ordered_array_add_get_one_el(void){
  ordered_array_add(ordered_array_int,&i1);
  TEST_ASSERT_EQUAL_PTR(&i1,ordered_array_get(ordered_array_int,0));
}

  
static void test_ordered_array_add_get_three_el(void){
  int* exp_arr[] = {&i1,&i2,&i3};
  ordered_array_add(ordered_array_int,&i1);
  ordered_array_add(ordered_array_int,&i2);
  ordered_array_add(ordered_array_int,&i3);
  int** act_arr = malloc(3*sizeof(int*));
  for(unsigned long i=0;i<3;i++)
    act_arr[i] = (int*)ordered_array_get(ordered_array_int,i);
  TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr,act_arr,3);
}



int main(void) {

  //test session
  UNITY_BEGIN();
  
  RUN_TEST(test_ordered_array_is_empty_zero_el);
  RUN_TEST(test_ordered_array_is_empty_one_el);
  RUN_TEST(test_ordered_array_size_zero_el);
  RUN_TEST(test_ordered_array_size_one_el);
  RUN_TEST(test_ordered_array_size_two_el);
  RUN_TEST(test_ordered_array_add_get_one_el);
  RUN_TEST(test_ordered_array_add_get_three_el);
  
  return UNITY_END();
}

