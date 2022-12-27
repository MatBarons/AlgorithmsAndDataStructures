/**
 * @file sorting.h
 * @author Stefano Cipolletta
 * @version v0.2
 * */

#ifndef SORTING_H_adsnoiqpfmoa
#define SORTING_H_adsnoiqpfmoa

#include "generic_array.h"

/**
 * Orders the array using the quick sort algorithm
 * @param unsorted_array unsorted array
 * @param compare pointer to a function which determines the precedence relation between the array elements.
 * It returns 1 iff the first element is greater than the second.
 * It returns -1 iff the first element is smaller than the second.
 * It returns 0 iff the first and the second elements are equal.
 * @pre GenericArray cannot be NULL
 * @pre *compare must be a reference to a valid function
 * @author Stefano Cipolletta
 * */
void quick_sort(GenericArray* unsorted_array, int (*compare)(void*, void*));

/**
 * Function that exchanges the first element with the second element.
 * @param unsorted_array pointer to a GenericArray structure containing the elements that have to be swapped
 * @param index1 first index of the first element to swap
 * @param index2 second index of the second element to swap
 * @return 1 on success, -1 otherwise
 * @pre GenericArray cannot be NULL
 * @author Stefano Cipolletta
 * */
int swap(GenericArray* unsorted_array, unsigned long index1, unsigned long index2);

/**
 * Orders the array using the binary insertion sort algorithm
 * @param unsorted_array unsorted array
 * @param compare pointer to a function which determines the precedence relation between the array elements.
 * It returns 1 iff the first element is greater than the second.
 * It returns -1 iff the first element is smaller than the second.
 * It returns 0 iff the first and the second elements are equal.
 * @return GenericArray pointer on success, NULL otherwise
 * @author Stefano Cipolletta
 * */
void* binary_insertion_sort(GenericArray* unsorted_array, int (*compare)(void*, void*));

#endif /*SORTING_H_adsnoiqpfmoa*/