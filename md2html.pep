init:    NOP0                
         LDA     0,i         
         LDX     0,i         
         STA     carac,d     
         STA     car,d       
         STX     h,d         
;
;
;
lecture: NOP0                
         LDA     0,i         
         CHARI   carac,d     
         LDBYTEA carac,d     
         CPA     '#',i       
         BREQ    diese       
         CPA     '\n',i      
         BREQ    entrer      
         CPA     '-',i       
         BREQ    list        
         LDX     h,d         
         BREQ    para        
         LDX     firstCa,d   
         BRNE    sLectu      
sLectu:  NOP0                
         LDX     1,i         
         STX     firstCa,d   
         STBYTEA car,d       ; car = carac;
         CHARO   carac,d     
         BR      lecture     
;
;
;
diese:   NOP0                ; if (carac == '#') {
         STBYTEA car,d       ; car = carac;
         LDX     h,d         
         ADDX    1,i         
         STX     h,d         ;h++;
         BR      titre       ;}
titre:   NOP0                
         CHARI   carac,d     
         LDBYTEA carac,d     
         CPA     '#',i       
         BREQ    diese       
         CPA     '\n',i      
         BREQ    entrer      
         CHARO   '<',i       
         CHARO   'h',i       
         DECO    h,d         
         CHARO   0,i         
         CHARO   '>',i       
         CHARO   carac,d     
         BR      text        
text:    NOP0                
         LDA     0,i         
         STBYTEA car,d       
         CHARI   carac,d     
         LDBYTEA carac,d     
         CPA     '\n',i      
         BREQ    entrer      
         CHARO   carac,d     
         BR      text        
;
;
;
entrer:  NOP0                ; if (carac == '\n') {
         LDA     0,i         
         LDBYTEA car,d       
         CPA     '#',i       
         BREQ    arret       ; if (car == '#') return (0);
         CPA     '\n',i      
         BRNE    entrerS     
         CHARO   carac,d     
         BR      lecture     
entrerS: NOP0                
         LDA     0,i         
         LDBYTEA carac,d     
         STBYTEA car,d       ; car = carac;
         LDX     h,d         
         BREQ    lecture     
         CHARO   '<',i       
         CHARO   '/',i       
         CHARO   'h',i       
         DECO    h,d         
         CHARO   0,i         
         CHARO   '>',i       
         CHARO   car,d       ; print("</h"+h+">\n");
         LDX     0,i         
         STX     h,d         
         STX     firstCa,d   
         BR      lecture     ; }
;
;
;
para:    NOP0                
         CHARO   '<',i       
         CHARO   'p',i       
         CHARO   0,i         
         CHARO   '>',i       
         CHARO   '\n',i      
         CHARO   carac,d     
         BR      textP       
textP:   NOP0                
         LDA     0,i         
         LDBYTEA carac,d     
         STBYTEA car,d       ; car = carac;
         CHARI   carac,d     
         LDBYTEA carac,d     
         CPA     '\n',i      
         BREQ    entrerP     
         CPA     '#',i       
         BREQ    dieseP      
         CHARO   carac,d     
         BR      textP       
entrerP: NOP0                
         LDBYTEA car,d       
         CPA     '\n',i      
         BREQ    p1          
         CHARO   carac,d     
         BR      textP       
p1:      NOP0                
         CHARO   '<',i       
         CHARO   '/',i       
         CHARO   'p',i       
         CHARO   0,i         
         CHARO   '>',i       
         CHARO   carac,d     
         CHARO   car,d       
         BR      lecture     
dieseP:  NOP0                
         CHARO   '<',i       
         CHARO   '/',i       
         CHARO   'p',i       
         CHARO   0,i         
         CHARO   '>',i       
         CHARO   '\n',i      
         BR      diese 
;
;
;      
list:    NOP0                
         CHARO   '<',i       
         CHARO   'u',i       
         CHARO   'l',i       
         CHARO   0,i         
         CHARO   '>',i       
         CHARO   '\n',i      
         BR      item        
item:    NOP0                
         STBYTEA car,d       
         LDBYTEA carac,d     
         CPA     '-',i       
         BREQ    li          
li:      NOP0                
         CHARO   '<',i       
         CHARO   'l',i       
         CHARO   'i',i       
         CHARO   0,i         
         CHARO   '>',i       
         BR      textL       
textL:   NOP0                
         LDA     0,i         
         LDBYTEA carac,d     
         STBYTEA car,d       ; car = carac;
         CHARI   carac,d
         LDBYTEA carac,d     
         CPA     '\n',i      
         BREQ    entrerL     
         CPA     '#',i       
         BREQ    dieseL   
         LDBYTEA car,d
         CPA     '\n',i       
         BREQ    tiret         
         CHARO   carac,d     
         BR      textL       
li1:     NOP0                
         CHARO   '<',i       
         CHARO   '/',i       
         CHARO   'l',i       
         CHARO   'i',i       
         CHARO   0,i         
         CHARO   '>',i 
         CHARO   '\n',i      
         CHARO   '<',i       
         CHARO   'l',i       
         CHARO   'i',i       
         CHARO   0,i         
         CHARO   '>',i       
         BR      textL       
entrerL: NOP0                
         LDBYTEA car,d       
         CPA     '\n',i      
         BREQ    l1
         STBYTEA car,d       ; car = carac;
         CHARI   carac,d
         LDBYTEA carac,d 
         CPA     '#',i      
         BREQ    dieseL
         CPA     '-',i      
         BREQ    li1
         CHARO   '\n',i
         CHARO   carac,d
         BR      textL       
l1:      NOP0                
         CHARO   '<',i       
         CHARO   '/',i       
         CHARO   'l',i       
         CHARO   'i',i       
         CHARO   0,i         
         CHARO   '>',i       
         CHARO   '\n',d     
         CHARO   '<',i       
         CHARO   '/',i       
         CHARO   'u',i       
         CHARO   'l',i       
         CHARO   0,i         
         CHARO   '>',i       
         CHARO   car,d       
         BR      lecture     
dieseL:  NOP0                
         CHARO   '<',i       
         CHARO   '/',i       
         CHARO   'l',i       
         CHARO   'i',i       
         CHARO   0,i         
         CHARO   '>',i       
         CHARO   '\n',i      
         CHARO   '<',i       
         CHARO   '/',i       
         CHARO   'u',i       
         CHARO   'l',i       
         CHARO   0,i         
         CHARO   '>',i       
         CHARO   '\n',i      
         BR      diese
tiret:   NOP0
         LDBYTEA carac,d    
         CPA     '-',i
         BREQ    li1
         BR         textL  
arret:   NOP0                ; fin du programme
         STOP                
;
;
;
carac:   .BYTE   0           ;caractere lu
car:     .BYTE   0           ;caractere precedent
h:       .WORD   0           ;nbre de balise html titre
firstCa: .WORD   0           
         .END                  