# Software-Engineer-Online-Challenge
This contains the  SQL Query Engine project. 
This is a Netbeans project designed using java.
The source code can be found at  Software-Engineer-Online-Challenge\sde\src\sde

Following are some examples:

SELECT * FROM products WHERE store=2                    //QUERY

title,brand,store,price,in_stock                        //OUTPUT
Apple iPhone 5,2,2,599.99,true
Nokia Lumia 800,3,2,300,false
Samsung LED TV 51 inches,4,2,900,true

SELECT brand FROM products WHERE price > 600            //QUERY

brand                                                    //OUTPUT
2
2
4
4
4

SELECT MAX(price) FROM products                         //QUERY

MAX(price)                                              //OUTPUT
1099.99

SELECT UNIQ(store) FROM products WHERE in_stock=false                    //QUERY

UNIQ(store)                                                              //OUTPUT                
1
2
5
6
7

SELECT title FROM products WHERE in_stock=false AND brand=5                     //QUERY

title                                                                           //OUTPUT
L'Oreal Hair Conditioner
L'Oreal Hair Conditioner

SELECT title FROM products WHERE in_stock=false AND ( brand=5 OR store=2 )                     //QUERY

title                                                                                          //OUTPUT
Nokia Lumia 800
L'Oreal Hair Conditioner
L'Oreal Hair Conditioner
