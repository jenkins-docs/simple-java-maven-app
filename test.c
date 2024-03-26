size_t
levenshtein(const char *a, const char *b) E
const size_t length = strlen(a);
const size_t bLength = stren (b);
return levenshtein_n(a, length, b, bLength);