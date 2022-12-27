/**
 * @file generic_array.c
 * @author Stefano Cipolletta
 * @version v0.2
 * */

#include "generic_array.h"

#include <stdio.h>
#include <stdlib.h>

#ifndef INITIAL_ARRAY_SIZE
#define INITIAL_ARRAY_SIZE 10
#endif

struct _GenericArray {
    void** array;
    unsigned long num_el;
    unsigned long size;
};

GenericArray* generic_array_create() {
    GenericArray* array_ptr = (GenericArray*)malloc(sizeof(GenericArray));
    if (array_ptr == NULL) {
        // fprintf(stderr, "generic_array_create(): Unable to allocate memory for the new array.\n");
        return NULL;
    }

    array_ptr->array = malloc(INITIAL_ARRAY_SIZE * sizeof(void*));
    if (array_ptr->array == NULL) {
        // fprintf(stderr, "generic_array_create(): Unable to allocate memory for the new array.\n");
        return NULL;
    }

    for (unsigned long i = 0; i < INITIAL_ARRAY_SIZE; ++i) {
        array_ptr->array[i] = NULL;
    }

    array_ptr->num_el = 0;
    array_ptr->size = INITIAL_ARRAY_SIZE;

    return (array_ptr);
}

unsigned long generic_array_size(GenericArray* array_ptr) {
    if (array_ptr == NULL) {
        // fprintf(stderr, "generic_array_size(): array_ptr parameter is NULL.\n");
        return 0;
    }
    return (array_ptr->num_el);
}

void* generic_array_insert(GenericArray* array_ptr, void* new_el_ptr) {
    if (array_ptr == NULL) {
        // fprintf(stderr, "generic_array_insert(): array_ptr parameter is NULL.\n");
        return NULL;
    }
    if (new_el_ptr == NULL) {
        // fprintf(stderr, "generic_array_insert(): new_el_ptr parameter is NULL.\n");
        return NULL;
    }
    if (array_ptr->num_el >= array_ptr->size) {
        array_ptr->array = realloc(array_ptr->array, 2 * (array_ptr->size) * sizeof(void*));
        if (array_ptr->array == NULL) {
            // fprintf(stderr, "generic_array_insert(): Unable to reallocate memory for the array.\n");
            return NULL;
        }
        // Sets new uninitialized pointers to NULL.
        for (unsigned long i = array_ptr->size; i < (2 * array_ptr->size); i++)
            array_ptr->array[i] = NULL;
        array_ptr->size = 2 * array_ptr->size;
    }
    (array_ptr->array)[array_ptr->num_el] = new_el_ptr;
    (array_ptr->num_el)++;
    return (array_ptr->array)[array_ptr->num_el - 1];
}

void* generic_array_update_at(GenericArray* array_ptr, void* new_el_ptr, unsigned long index) {
    if (array_ptr == NULL) {
        // fprintf(stderr, "generic_array_update_at(): array_ptr parameter is NULL.\n");
        return NULL;
    }
    if (new_el_ptr == NULL) {
        // fprintf(stderr, "generic_array_update_at(): new_el_ptr parameter is NULL.\n");
        return NULL;
    }
    if (index >= array_ptr->num_el) {
        // fprintf(stderr, "generic_array_update_at(%lu): index out of bound.\n", index);
        return NULL;
    }
    (array_ptr->array)[index] = new_el_ptr;
    return (array_ptr->array)[index];
}

void* generic_array_get(GenericArray* array_ptr, unsigned long index) {
    if (array_ptr == NULL) {
        // fprintf(stderr, "generic_array_get(): array_ptr parameter is NULL.\n");
        return NULL;
    }
    if (index >= array_ptr->num_el) {
        // fprintf(stderr, "generic_array_get(%lu): index out of bound.\n", index);
        return NULL;
    }
    return (array_ptr->array)[index];
}

int generic_array_clear(GenericArray* array_ptr) {
    if (array_ptr == NULL) {
        // fprintf(stderr, "generic_array_clear(): array_ptr parameter is NULL.\n");
        return -1;
    }

    for (unsigned long i = 0; i < array_ptr->num_el; ++i) {
        if (array_ptr->array[i] != NULL) {
            free(array_ptr->array[i]);
            array_ptr->array[i] = NULL;
        }
    }

    array_ptr->num_el = 0;
    return 1;
}

int generic_array_destroy(GenericArray* array_ptr) {
    if (array_ptr == NULL) {
        // fprintf(stderr, "generic_array_destroy(): array_ptr parameter is NULL.\n");
        return -1;
    }

    free(array_ptr->array);
    free(array_ptr);
    return 1;
}