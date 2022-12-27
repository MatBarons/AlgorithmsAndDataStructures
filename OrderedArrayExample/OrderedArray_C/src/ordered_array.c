#include <stdlib.h>
#include <stdio.h>
#include "ordered_array.h"

//Initial capacity for the array
#define INITIAL_CAPACITY 2


static unsigned long  get_index_to_insert(OrderedArray *ordered_array, void* element);
static void insert_element(OrderedArray *ordered_array, void* element,unsigned long index);

//It represents the internal structure of this implementation of ordered arrays
struct _OrderedArray{
  void** array;
  unsigned long el_num;
  unsigned long array_capacity;
  int (*precedes)(void*,void*);
};

OrderedArray *ordered_array_create(int (*precedes)(void*,void*)){
  if(precedes == NULL){
    fprintf(stderr,"ordered_array_create: precedes parameter cannot be NULL");
    exit(EXIT_FAILURE);
  }
  OrderedArray *ordered_array = (OrderedArray *)malloc(sizeof(OrderedArray));
  if(ordered_array == NULL){
    fprintf(stderr, "ordered_array_create: unable to allocate memory for the ordered array");
    exit(EXIT_FAILURE);
  }
  ordered_array->array = (void**)malloc(INITIAL_CAPACITY*sizeof(void*));
  if(ordered_array->array == NULL){
    fprintf(stderr, "ordered_array_create: unable to allocate memory for the internal array");
    exit(EXIT_FAILURE);
  }
  ordered_array->el_num = 0;
  ordered_array->array_capacity =INITIAL_CAPACITY;
  ordered_array->precedes = precedes;
  
  return(ordered_array);
}


int ordered_array_is_empty(OrderedArray *ordered_array){
  if(ordered_array == NULL){
    fprintf(stderr,"ordered_array_is_empty: ordered_array parameter cannot be NULL");
    exit(EXIT_FAILURE);
  }
  if(ordered_array->el_num == 0)
    return(1);
  return(0);
}


unsigned long ordered_array_size(OrderedArray *ordered_array){
  if(ordered_array == NULL){
    fprintf(stderr,"ordered_array_size: ordered_array parameter cannot be NULL");
    exit(EXIT_FAILURE);
  }
  return(ordered_array->el_num);
}


void ordered_array_add(OrderedArray *ordered_array, void* element){
  if(ordered_array == NULL){
    fprintf(stderr,"add_ordered_array_element: ordered_array parameter cannot be NULL");
    exit(EXIT_FAILURE);
  }
  if(element == NULL){
    fprintf(stderr,"add_ordered_array_element: element parameter cannot be NULL");
    exit(EXIT_FAILURE);
  }
  
  if(ordered_array->el_num >= ordered_array->array_capacity){
    ordered_array->array = (void**)realloc(ordered_array->array,2*(ordered_array->array_capacity)*sizeof(void*));
    if(ordered_array->array == NULL){
      fprintf(stderr,"ordered_array_add: unable to reallocate memory to host the new element");
      exit(EXIT_FAILURE);
    }
    ordered_array->array_capacity = 2*ordered_array->array_capacity;
  }
  
  unsigned long index = get_index_to_insert(ordered_array, element);
  
  insert_element(ordered_array,element,index);
  
  (ordered_array->el_num)++;
  
}


void* ordered_array_get(OrderedArray *ordered_array, unsigned long i){
  if(ordered_array == NULL){
    fprintf(stderr,"ordered_array_get: ordered_array parameter cannot be NULL");
    exit(EXIT_FAILURE);
  }
  if(i>=ordered_array->el_num){
    fprintf(stderr,"ordered_array_get: Index %lu is out of the array bounds",i);
    exit(EXIT_FAILURE);
  }
  return(ordered_array->array)[i];
}


void ordered_array_free_memory(OrderedArray *ordered_array){
  if(ordered_array == NULL){
    fprintf(stderr,"ordered_array_free_memory: ordered_array parameter cannot be NULL");
    exit(EXIT_FAILURE);
  }
  free(ordered_array->array);
  free(ordered_array);
}



//returns the position where the new element must be inserted
static unsigned long get_index_to_insert(OrderedArray *ordered_array, void* element){
  unsigned long i = 0;
  int cont = 1;
  while(i<ordered_array->el_num && cont){
    if((*(ordered_array->precedes))(element,(ordered_array->array)[i])){
      cont = 0;
    } else i++;
  }
  
  return(i);
}


//inserts the specified element in the specified ordered array at the specified
//index position
static void insert_element(OrderedArray *ordered_array, void* element,unsigned long index){
  for(unsigned long i = ordered_array->el_num;i>index;i--){
    (ordered_array->array)[i] = (ordered_array->array)[i-1];
  }
  (ordered_array->array)[index] = element;
}
