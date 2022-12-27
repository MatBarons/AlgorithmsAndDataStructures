/**
 * @file generic_array.h
 * @author Stefano Cipolletta
 * @version v0.2
 * */

#ifndef GENERIC_ARRAY_H_adsnoiqpfmoa
#define GENERIC_ARRAY_H_adsnoiqpfmoa

/**
 * An array of an undefined number of elements of any possible type
 * @author Stefano Cipolletta
 * */
typedef struct _GenericArray GenericArray;

/**
 * Creates an empty generic array and initialize its elements to NULL.
 * @return its pointer on success, NULL otherwise
 * @author Stefano Cipolletta
 * */
GenericArray* generic_array_create();

/**
 * @param array_ptr pointer to a generic array
 * @return its current size on success, 0 otherwise
 * @author Stefano Cipolletta
 * */
unsigned long generic_array_size(GenericArray* array_ptr);

/**
 * Adds the element after the last inserted
 * @param array_ptr pointer to a generic array
 * @param new_el_ptr pointer to an element
 * @return the pointer to the inserted element on success, NULL otherwise
 * @author Stefano Cipolletta
 * */
void* generic_array_insert(GenericArray* array_ptr, void* new_el_ptr);

/**
 * Modify an element in the array_ptr at the given position
 * @param array_ptr pointer to a generic array
 * @param new_el_ptr pointer to an element
 * @param index position to update
 * @return the pointer to the updated element on success, NULL otherwise
 * @author Stefano Cipolletta
 * */
void* generic_array_update_at(GenericArray* array_ptr, void* new_el_ptr, unsigned long index);

/**
 * @param array_ptr pointer to a generic array
 * @param index generic array index
 * @return a pointer to the element in that position, NULL otherwise
 * @author Stefano Cipolletta
 * */
void* generic_array_get(GenericArray* array_ptr, unsigned long index);

/**
 * Clears all the elements stored in the generic array, freeing their occupied memory.
 * It does not destroy the structure.
 * Use with caution, as it could lead to segmentation faults if the array contains pointers to reserved memory locations.
 * @param array_ptr pointer to a generic array
 * @return 1 on success, -1 otherwise
 * @author Stefano Cipolletta
 * */
int generic_array_clear(GenericArray* array_ptr);

/**
 * Frees the memory occpied by the structure of the generic array, leaving the task of freeing emory occupied by its elements to the calling program.
 * @param array_ptr pointer to a generic array
 * @return 1 on success, -1 otherwise
 * @author Stefano Cipolletta
 * */
int generic_array_destroy(GenericArray* array_ptr);

#endif /*GENERIC_ARRAY_H_adsnoiqpfmoa*/