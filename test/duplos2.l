/* deveria funcionar  */

while (naoTerminou || TRUE) 
	begin 
		while (naoTerminou || TRUE) begin 
		  writeln("Ola' ",nome);
		  n<-n+1;
		  naoTerminou<-n<MAXITER;
		  while (naoTerminou || TRUE) 
			begin 
				while (naoTerminou || TRUE) 
					begin 
					  writeln("Ola' ",nome);
					  n<-n+1;
					  naoTerminou<-n<MAXITER;
					endwhile
			endwhile
	endwhile
endwhile

