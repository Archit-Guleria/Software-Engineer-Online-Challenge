# Software-Engineer-Online-Challenge
This contains the  SQL Query Engine project. 
This is a Netbeans project designed using java.
The source code can be found at  Software-Engineer-Online-Challenge\sde\src\sde

Following are some Queries that can be implemented using this search engine:

SELECT * FROM products WHERE store=2                  

SELECT brand FROM products WHERE price > 600          

SELECT MAX(price) FROM products                        

SELECT UNIQ(store) FROM products WHERE in_stock=false                   

SELECT title FROM products WHERE in_stock=false AND brand=5                     

SELECT title FROM products WHERE in_stock=false AND ( brand=5 OR store=2 )                

