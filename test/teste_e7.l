/* Teste */

/* somatorio */
int x;
int n;
int i;

/* somatorio */
x <- 0;
i <- 1;
n <- 10;
while (i <= n) begin
  x <- x + i;
endwhile

/* prints */
write("hello");
writeln("A soma e: ", x);

if( x > 10) begin
  writeln("x maior que 10.");
  writeln("segunda linha do if");
endif
else begin
  writeln("x menor que 10.");
endelse