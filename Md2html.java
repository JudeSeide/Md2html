/*
 * 
 * 	Application de convertion de document au format mini-markdown en document au format html. 
 *	Seul un sous-ensemble du format markdown sera à réaliser
 *
 *	Auteurs: Jude Seide 
 *
 */

import java.io.IOException;

public class Md2html{
	
	//constante du saut de ligne
	public static final char SAUT_DE_LIGNE = '\n';
   
	//constante du retour-chariot 
	public static final char RETOUR_CHARIOT = '\r';
	
	//constante qui exprime la fin de lecture ou un titre
	public static final char DIESE = '#';
	
	//constante qui marque chaque element d'une liste
	public static final char SCORE = '-';
	
	//variable globale permettan d'arreter le programme au bon moment
	public static boolean fin = true;
	
	public static String titre(char c){
		boolean lecture = true;
		char carac = c;
		char car = 0;
		int h = 0;
		int temp = 0;
		String html1 = "";
		String html2 = "";
		while(lecture){
        	try {        		
        		if(carac == DIESE){
        			car = carac;
        			++temp;
        		}else if(carac != SAUT_DE_LIGNE){
                    car = 0;
                    h = temp;
                    html1 += caracSpec(carac);
        		}else if(carac == SAUT_DE_LIGNE && html1 != ""){
        			temp = 0;
        			if(car == SAUT_DE_LIGNE){
        				html2 += carac;
        			}else{
        				html2 +="<h"+h+">"+html1+"</h"+h+">"+ carac;
        			}
        			car = carac;
        			html1 = "";
        		}
        		if(carac == SAUT_DE_LIGNE && car == DIESE){
  				  	lecture = false;
  				  	fin = false;
  				}else if (car == SAUT_DE_LIGNE && carac != DIESE){
  					lecture = false;
  				}
        		if(lecture){
        			carac = (char) System.in.read();
        		}        		
            } catch (IOException e) {
                 e.printStackTrace();
            }
        }
		
		return html2;
	}
		
	public static String paragraphe(char carac){        
        boolean lecture = true;  
        String html1 = "";
        char car = carac;
        String html2 = "";
        String eol = "";
        if(carac != SAUT_DE_LIGNE){
        	html2 += caracSpec(carac);
        }else{
        	eol += carac;
        }
        
        while(lecture){
                try{ 
                		carac = (char) System.in.read();
                		if(carac == DIESE){
                        	lecture = false;
                        }else{
                        	if(carac != SAUT_DE_LIGNE){                        		
                        		html1 += caracSpec(carac);
                        	}else if(carac == SAUT_DE_LIGNE && car != SAUT_DE_LIGNE){
                        		html2 += html1 + carac;
                        		html1 = "";
                        	}else if(carac == SAUT_DE_LIGNE && car == SAUT_DE_LIGNE){
                        		html2 = "<p>\n" + html2 + "</p>" + car + carac;
                        		lecture = false;
                        	}
                        	car = carac;
                        }                    	                    	
                }catch(IOException e){
                        e.printStackTrace();
                }                                
        }
        if(carac == DIESE){
        	if(car == SAUT_DE_LIGNE){
        		html2 = eol + "<p>\n" + html2 + "</p>\n";
        	}
        	html2 += titre(carac);
        }
        return html2;
	}
	
	public static String liste(char carac){
        boolean lecture = true;
        char car = carac;
       
        String html1 = "";
        String html2 = "";
        if(carac != SCORE){
        	html2 += caracSpec(carac);
        }
        while(lecture){
                try{
                	carac = (char) System.in.read();
                	if(carac == DIESE){
                    	lecture = false;
                    }else{
                    	if(carac != SAUT_DE_LIGNE){
                    		if(carac == SCORE && car != SAUT_DE_LIGNE){
                    			html1 += carac;
                    		}else if(carac == SCORE && car == SAUT_DE_LIGNE){
                    			html2 += "<li>"+ html1 + "</li>" + car;
                    			html1 = "";
                    		}else if(carac != SCORE && car == SAUT_DE_LIGNE){
                    			html1 += car ;
                    		}
                    		if(carac != SCORE){
                    			html1 += caracSpec(carac) ;
                    		}
                    	}
                    	
                    	if(carac == SAUT_DE_LIGNE && car == SAUT_DE_LIGNE){
                    		html2 = "<ul>\n" + html2 + "</ul>" + carac;
                    		lecture = false;
                    	} 
                    }  
                	car = carac;
                }catch(IOException e){
                        e.printStackTrace();
                }
        }
        if(carac == DIESE){   
        	html2 = "<ul>\n" + html2 + "<li>"+ html1 + "</li>" + "\n</ul>";
        	html2 += titre(carac);
        }
		return html2;
	}
	
	public static String caracSpec(char carac){
		String car = "";
		car += carac;
		if(carac == '<'){
			car = "&lt;";
		}else if(carac == '>'){
			car = "&gt;";
		}else if(carac == '&'){
			car = "&amp;";
		}
		return car;
	}
        
		   
	/**
	 * @param args
     */
	public static void main(String[] args) {
		char carac = 0;
		
		String html = "";
        while(fin){
        	
        	try {
        		do{
        			carac = (char) System.in.read();
        			if(carac == SAUT_DE_LIGNE)
        			html+="\n";
        		}while(carac == SAUT_DE_LIGNE);        		
        		
        		if(carac == DIESE){
        			html += titre(carac);
        		}else if(carac != DIESE && carac != SCORE){
        			html += paragraphe(carac);
        		}else if (carac == SCORE) {
                    html += liste(carac);
        		}
        		
        		        		
            } catch (IOException e) {
                 e.printStackTrace();
            }
                       
        }
        System.out.print(html);
	}

}
