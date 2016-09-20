/* teste */
int n; /* asdasda */
string nome;
boolean naoTerminou;
final MAXITER<-10;
int x <- 0x1d;

write("Digite seu nome: ");
readln(nome);
naoTerminou<-TRUE;
n<-1;
while (naoTerminou | TRUE) begin /* erro lex */
  writeln("Ola' ",nome);
  n<-n+1;
  naoTerminou<-n<MAXITER;
endwhile
