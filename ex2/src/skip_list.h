/**
 *  @author Stefano Cipolletta
 *  @file skip_list.h
 *  @version 0.1
 * */

#ifndef _SKIP_LIST_H_asoiwnfiualqi
#define _SKIP_LIST_H_asoiwnfiualqi

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define MAX_HEIGHT 20

typedef struct _SkipList SkipList;
typedef struct _Node Node;

struct _SkipList {
    Node *head;
    int max_level;  // current max value of size of all Node
    int (*compare)(void *, void *);
};

struct _Node {
    Node **next;
    int size;  // number of node pointers
    void *item;
};

/**
 *  @param item pointer to the item to insert into the new Node
 *  @param level number of level that the new Node will have
 *  @return the pointer to the new Node
 * */
Node *createNode(void *item, int level);

/**
 *  @return the number of level of a Node
 * */
int randomLevel();

/**
 *  @param compare pointer to the function that compare two generic type
 *      - it returns an int > 0 if the first is greater than the second
 *      - it returns an int < 0 if the first is less than the second
 *      - it return an int == 0 if the first is equal to the second
 *  @return the pointer to the new SkipList
 * */
SkipList *createSkipList(int (*compare)(void *, void *));

/**
 *  @param list pointer to the SkipList
 *  @param item pointer to the item to insert into the SkipList
 *  @return 1 on success, -1 otherwise
 * */
int insertSkpiList(SkipList *list, void *item);

/**
 *  @param list pointer to the SkipList
 *  @param item pointer to the item to search into the SkipList
 * */
int searchSkipList(SkipList *list, void *item);

void freeNode(Node *n, void (*custom_free)(void *));

void freeSkipList(SkipList *l, void (*custom_free)(void *));

#endif /* _SKIP_LIST_H_asoiwnfiualqi */