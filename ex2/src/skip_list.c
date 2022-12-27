#include "./skip_list.h"

Node* createNode(void* item, int level) {
    Node* n = (Node*)malloc(sizeof(Node));
    if (n == NULL) {
        fprintf(stderr, "createNode(): node malloc error\n");
        return NULL;
    }

    n->item = item;
    n->size = level;
    n->next = (Node**)malloc((unsigned long int)(level) * sizeof(Node*));

    if (n->next == NULL) {
        fprintf(stderr, "createNode(): next malloc error\n");
        return NULL;
    }

    return n;
}

int randomLevel() {
    int lvl = 1;

    srand((unsigned)time(NULL));

    while ((rand() % 2) < 0.5 && lvl < MAX_HEIGHT)
        lvl++;

    return lvl;
}

SkipList* createSkipList(int (*compare)(void*, void*)) {
    SkipList* l = (SkipList*)malloc(sizeof(SkipList));
    if (l == NULL) {
        fprintf(stderr, "createSkipList(): skiplist malloc error\n");
        return NULL;
    }
    l->head = (Node*)malloc(sizeof(Node));
    if (l->head == NULL) {
        fprintf(stderr, "createSkipList(): skiplist l->head error\n");
        return NULL;
    }

    if ((l->head = createNode(NULL, MAX_HEIGHT)) == NULL) {
        fprintf(stderr, "createSkipList(): skiplist createNode error\n");
        return NULL;
    }

    l->max_level = 1;
    l->compare = compare;
    return l;
}

int insertSkpiList(SkipList* list, void* item) {
    Node* n;
    if ((n = createNode(item, randomLevel())) == NULL) {
        // fprintf(stderr, "insertSkipList(): skiplist createNode error\n");
        // exit(EXIT_FAILURE);
        return -1;
    }
    if (n->size > list->max_level)
        list->max_level = n->size;

    Node* x = list->head;
    for (int k = list->max_level - 1; k >= 0; --k) {
        if (x->next[k] == NULL || list->compare(item, x->next[k]->item) < 0) {
            if (k < n->size) {
                n->next[k] = x->next[k];
                x->next[k] = n;
            }
        } else {
            x = x->next[k];
            k++;
        }
    }
    return 1;
}

int searchSkipList(SkipList* list, void* item) {
    Node* x = list->head;

    // loop invariant: x->item < I
    for (int i = list->max_level - 1; i >= 0; --i) {
        while (x->next[i] != NULL && list->compare(x->next[i]->item, item) < 0) {
            x = x->next[i];
        }
    }

    // x->item < I <= x->next[1]->item
    x = x->next[0];
    if (x->item != NULL && (list->compare(x->item, item) == 0))
        return 1;
    else
        return -1;
}

void freeNode(Node* n, void (*custom_free)(void*)) {
    if (custom_free != NULL)
        custom_free(n->item);

    n->item = NULL;
    free(n->next);
    free(n);
    n = NULL;
}

void freeSkipList(SkipList* l, void (*custom_free)(void*)) {
    Node* current = l->head;
    Node* next = NULL;

    // printf("\nDeleting SkipList ...\n");

    while (current) {
        next = current->next[0];
        freeNode(current, custom_free);
        current = next;
    }

    // printf("\nSkipList Deleted!\n");
}