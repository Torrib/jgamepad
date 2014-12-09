gcc -c test.c -fPIC
gcc -shared -fPIC -o libtest.so test.o
