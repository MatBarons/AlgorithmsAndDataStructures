#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ordered_array.h"

/*
 * 
 */


struct record{
  char* string_field;
  int integer_field;
};

//It takes as input two pointers to struct record.
//It returns 1 iff the integer field of the first record is less than 
//the integer field of the second one (0 otherwise)
static int precedes_record_int_field(void* r1_p,void* r2_p){
  if(r1_p == NULL){
    fprintf(stderr,"precedes_record_int_field: the first parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  if(r2_p == NULL){
    fprintf(stderr,"precedes_record_int_field: the second parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  struct record *rec1_p = (struct record*)r1_p;
  struct record *rec2_p = (struct record*)r2_p;
  if(rec1_p->integer_field < rec2_p->integer_field){
    return(1);
  }
  return(0);
}


///It takes as input two pointers to struct record.
//It returns 1 iff the string field of the first record is less than 
//the string field of the second one (0 otherwise)
static int precedes_record_string_field(void* r1_p,void* r2_p){
  if(r1_p == NULL){
    fprintf(stderr,"precedes_string: the first parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  if(r2_p == NULL){
    fprintf(stderr,"precedes_string: the second parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  struct record *rec1_p = (struct record*)r1_p;
  struct record *rec2_p = (struct record*)r2_p;
  if(strcmp(rec1_p->string_field,rec2_p->string_field)<0){
    return(1);
  }
  return(0);
}


static  void free_array(OrderedArray* array) {
  unsigned long  el_num =  ordered_array_size(array);
  for(unsigned long i=0;i<el_num;i++){
    struct record *array_element = (struct record *)ordered_array_get(array, i);
    free(array_element->string_field);
    free(array_element);
  }
  ordered_array_free_memory(array);
}

static  void print_array(OrderedArray* array){
  unsigned long el_num =  ordered_array_size(array);
  
  struct record *array_element;
  
  printf("\nORDERED ARRAY OF RECORDS\n");
  
  for(unsigned long i=0;i<el_num;i++){
    array_element = (struct record *)ordered_array_get(array, i);
    printf("<%s,%d>\n",array_element->string_field,array_element->integer_field); 
  }
}

static void load_array(const char* file_name, OrderedArray* array){
  char *read_line_p;
  char buffer[1024];
  int buf_size = 1024;
  FILE *fp;
  printf("\nLoading data from file...\n");
  fp = fopen(file_name,"r");
  if(fp == NULL){
    fprintf(stderr,"main: unable to open the file");
    exit(EXIT_FAILURE);
  }
  while(fgets(buffer,buf_size,fp) != NULL){  
    read_line_p = malloc((strlen(buffer)+1)*sizeof(char));
    if(read_line_p == NULL){
      fprintf(stderr,"main: unable to allocate memory for the read line");
      exit(EXIT_FAILURE);
    }   
    strcpy(read_line_p,buffer);   
    char *string_field_in_read_line_p = strtok(read_line_p,",");
    char *integer_field_in_read_line_p = strtok(NULL,",");  
    char *string_field = malloc((strlen(string_field_in_read_line_p)+1)*sizeof(char));
    if(string_field == NULL){
      fprintf(stderr,"main: unable to allocate memory for the string field of the read record");
      exit(EXIT_FAILURE);
    }  
    strcpy(string_field,string_field_in_read_line_p);
    int integer_field = atoi(integer_field_in_read_line_p);  
    struct record *record_p = malloc(sizeof(struct record));
    if(record_p == NULL){
      fprintf(stderr,"main: unable to allocate memory for the read record");
      exit(EXIT_FAILURE);
    }   
    record_p->string_field = string_field;
    record_p->integer_field = integer_field;
    ordered_array_add(array, (void*)record_p);
    free(read_line_p);
  }
  fclose(fp);
  printf("\nData loaded\n");
}

static  void test_with_comparison_function(const char* file_name, int (*compare)(void*, void*)) {
  OrderedArray* array = ordered_array_create(compare);
  load_array(file_name, array);
  print_array(array);
  free_array(array);
}

//It should be invoked with one parameter specifying the filepath of the data file
int main(int argc, char const *argv[]) {
  if(argc < 2) {
    printf("Usage: ordered_array_main <file_name>\n");
    exit(EXIT_FAILURE);
  }
  test_with_comparison_function(argv[1], precedes_record_int_field);
  test_with_comparison_function(argv[1], precedes_record_string_field);   
  
  return (EXIT_SUCCESS);
}





