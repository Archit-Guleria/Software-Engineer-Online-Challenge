# Software-Engineer-Online-Challenge
This contains the  SQL Query Engine project. 
This is a Netbeans project designed using java.
The source code can be found at  Software-Engineer-Online-Challenge\sde\src\sde

The SQL engine can implement some new queries as well in addition to those mentioned in the challenge problem.

Following are some Queries that can be implemented using this search engine:

SELECT * FROM products WHERE store=2  

SELECT brand FROM products WHERE price > 600  

SELECT brand,title,in_stock FROM products WHERE price > 600 AND store>=2  

SELECT MAX(price) FROM products 

SELECT MAX(price) FROM products WHERE in_stock=true

SELECT MIN(price) FROM products 

SELECT MIN(price) FROM products WHERE in_stock=true

SELECT UNIQ(store) FROM products

SELECT UNIQ(store) FROM products WHERE price>600                  

SELECT title FROM products WHERE in_stock=false AND brand=5                     

SELECT title FROM products WHERE in_stock=false AND ( brand=5 OR store=2 )     

SELECT title FROM products WHERE (in_stock=true AND  brand>=4) OR price>700

