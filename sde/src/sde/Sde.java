package sde;
import java.util.*;
import java.io.*;
public class Sde 
{
    static ArrayList<String> AL=new ArrayList<String>();     //stores the attributes(sub headings) in a sequential order,as they are in the database
    static LinkedHashMap<String,LinkedList<String>> m=new LinkedHashMap<String,LinkedList<String>>(); //a map which is a 2D representation of the database
    static LinkedList<String> arr[];               //arr is an array of linkedlists
    
   

static ArrayList<Integer> Select(String str)                    //to select the attributes
{
     LinkedList<String> aLs=new LinkedList<String>();           //store the selected attributes individually in a list 
     ArrayList<Integer> L=new ArrayList<Integer>();             //store the selected indexes of attributes as per their order in AL
     
     if(str.compareTo("*")==0)
     {
         aLs.addAll(AL);
         
     }
     else
     {
     Scanner sc=new Scanner(str);
     sc.useDelimiter(",");                      //using commas as seperators
     while(sc.hasNext())
     aLs.add(sc.next());
      sc.close();
     }
     int i=0;
      while(!aLs.isEmpty())
         {
             if(aLs.peek().compareTo(AL.get(i))==0)
         {
             L.add(i);
             aLs.pop();
             i=0;
         }
         else 
             {
             i++;
             if(i>=AL.size())
             {
                 System.out.println("The attributes entered are not available in the database");
                 break;
             }
             }
         }
      return L;
}
    

    static TreeSet<Integer> solve(String str)   // solves the sub conditions as passed by WHERE function
    {
    Scanner sc=new Scanner(str);
    sc.useDelimiter(">=|<=|=|>|<");               //uses operators as seperators
    TreeSet<Integer> set=new TreeSet<Integer>();  //set to store the indexes( or row numbers) of the solutions in linked list
    String g=sc.next().trim();                    // string to store the variable or the attribute name being compared 
    String comp=sc.next().trim();                 //string to store the value with which the comparison has to be made 
    String op=str.substring(str.indexOf(g)+g.length(),str.indexOf(comp)).trim(); //stores the operator
     sc.close();
    int index=0;
    switch(op)
    {
        case ">":
            index=0;
             while(index<m.get(g).size())
             {
                 if( Double.parseDouble(m.get(g).get(index)) > Double.parseDouble(comp))
                 {
                     set.add(index);
                     index++;
                 }
            else index++;
             }
            break;
            
        case "<": 
            index=0;
             while(index<m.get(g).size())
             {
                 if( Double.parseDouble(m.get(g).get(index)) < Double.parseDouble(comp))
                 {
                     set.add(index);
                     index++;
                 }
            else index++;
             }
            break;
            
        case "=":
            index=0;
             while(index<m.get(g).size())
             {
                 if( m.get(g).get(index).trim().compareToIgnoreCase(comp) == 0)
                 {
                     set.add(index);
                     index++;
                 }
            else index++;
             }
            break;
            
        case ">=":
             index=0;
             while(index<m.get(g).size())
             {
                 if( Double.parseDouble(m.get(g).get(index)) > Double.parseDouble(comp)||Double.parseDouble(m.get(g).get(index)) == Double.parseDouble(comp))
                 {
                     set.add(index);
                     index++;
                 }
            else index++;
             }
            break;
            
        case "<=":
             index=0;
             while(index<m.get(g).size())
             {
                 if( Double.parseDouble(m.get(g).get(index)) < Double.parseDouble(comp)||Double.parseDouble(m.get(g).get(index)) == Double.parseDouble(comp))
                 {
                     set.add(index);
                     index++;
                 }
            else index++;
             }
            break;
    }
    return set;
    }

    static TreeSet<Integer> Where(String str)   // breaks the where condition into sub conditions
    {
    TreeSet<Integer> set1=new TreeSet<Integer>();   // clubs the results of different sub conditions
    
    if(str.contains("AND")||str.contains("OR"))
    {
        if( ( str.contains("AND") && str.contains("OR") ) || str.contains("(") )
        {
            if( ( str.contains("AND") && str.contains("OR") ) && !str.contains("(") )
            {
                System.out.println("Please use proper brackets in where condition ");
                System.exit(0);
            }
            
            set1.addAll(Where(str.substring(str.indexOf("(")+1,str.lastIndexOf(")")).trim()));
            
            StringBuffer strbf=new StringBuffer(str);
            strbf.delete(str.indexOf("("),str.lastIndexOf(")"));
            strbf.deleteCharAt(strbf.lastIndexOf(")"));
            
            if(strbf.indexOf("AND")>0){
           strbf.delete(strbf.indexOf("AND"),strbf.indexOf("AND")+3);
            set1.retainAll(solve(strbf.toString().trim()));
            }
            
            else  if(strbf.indexOf("OR")>0)
            {
           strbf.delete(strbf.indexOf("OR"),strbf.indexOf("OR")+2);
            set1.addAll(solve(strbf.toString().trim()));
            }
        }
        else if(str.contains("AND"))
        {
            StringBuffer strbf = new StringBuffer(str);
            String temp=strbf.substring(strbf.lastIndexOf("AND")+3).trim();
            set1.addAll(solve(temp));
             set1.retainAll(Where(strbf.substring(0,strbf.lastIndexOf("AND")-1)));
        }
        else if(str.contains("OR"))
        {
            StringBuffer strbf = new StringBuffer(str);
            String temp=strbf.substring(strbf.lastIndexOf("OR")+2).trim();
              set1.addAll(solve(temp));
               set1.addAll(Where(strbf.substring(0,strbf.lastIndexOf("OR")-1)));
        }
    }
    else
    set1.addAll(solve(str));               //when there are no sub conditions i.e. only one condition without the use of AND,OR
    return set1;
    }
          
    
    

    
    public static void main(String[] args) throws IOException{                   //main function
        FileReader  r=new FileReader("sql_engine_dataset.csv");                  //reads the database file
        Scanner s=new Scanner(r);
        String line="";
        int count_headings=0;
        
        
        
        if(s.hasNextLine())
            line=s.next();
        else 
        System.out.println("Titles missing");
        
        
        
        Scanner scan=new Scanner(line);
        scan.useDelimiter(",");                                            //sets comma to be used as a seperator
        while(scan.hasNext())
        {
            count_headings++;                                             //count the number of sub headings
            m.put(scan.next(),null);                                      //puts sub headings or attributes as keys in the map m
        }
        
        
        
       arr=new LinkedList[count_headings];                       //creates as many linked lists as there are sub headings or attributes
        for(int count=0;count<count_headings;count++)
            arr[count]=new LinkedList<String>();
        
       
        while(true)
        {
           if(s.hasNextLine())
           {
               StringBuffer line1=new StringBuffer(s.next()+" ");
               while(line1.indexOf(",")<0)
               {
                   line1.append(s.next()+" ");
               }
               scan=new Scanner(line1.toString());
               int count=0;
               scan.useDelimiter(",");
               while(count<count_headings)
               {
                   arr[count].add(scan.next());       //puts the values in different linked lists or columns as per the attributes
                   count++;
               }
           }
               else
               break;
        }
             
         
              int count=0;
               for(Map.Entry<String,LinkedList<String>> me:m.entrySet()){
                   m.put(me.getKey(),arr[count]);     //assigns the linked lists to their corresponding attributes 
                   count++;
               }
               scan.close();
//
               
              for(Map.Entry<String,LinkedList<String>> me:m.entrySet())
              {
                   AL.add(me.getKey());
               }


            s=new Scanner(System.in);
            StringBuffer temp=new StringBuffer(s.nextLine());
            String x=temp.toString();
            String str=new String();
            int flag=0;
            if(x.contains("UNIQ"))
            {
                if(!x.contains("WHERE"))                            //finds unique values when there is no condition
                {
                   str=temp.substring(x.indexOf("(")+1,x.indexOf(")"));
                   TreeSet<String> unq=new TreeSet<String>();
                   unq.addAll(m.get(str));
                   Iterator<String> it=unq.iterator();
                   System.out.println("UNIQ("+str+")");
                   while(it.hasNext())
                       System.out.println(it.next());
                   System.exit(0);                
                }
                else                                                  //finds unique values when there is a condition
                {
              temp.delete(x.indexOf("UNIQ"),x.indexOf("(")+1); 
              temp.deleteCharAt(temp.indexOf(")"));
              flag=1;
                }
            }
           
            System.out.println();
            
            if(x.contains("MAX"))
            {
                if(!x.contains("WHERE"))                                        //finds maximum value when there is no condition
                {
                String res= x.substring(x.indexOf('(')+1,x.indexOf(')')).trim();
                TreeSet<Double> t=new TreeSet<Double>();
                for(String sr:m.get(res))
                t.add(Double.parseDouble(sr));
               System.out.println(t.last());
               System.exit(0);
                }
               temp.delete(x.indexOf("MAX"),x.indexOf("(")+1);                   //finds maximum value when there is a condition
              temp.deleteCharAt(temp.indexOf(")"));
              flag=2;
            }
            
              if(x.contains("MIN"))
            {
                if(!x.contains("WHERE"))                                        //finds minimum value when there is no condition
                {
                String res= x.substring(x.indexOf('(')+1,x.indexOf(')')).trim();
                TreeSet<Double> t=new TreeSet<Double>();
                for(String sr:m.get(res))
                t.add(Double.parseDouble(sr));
               System.out.println(t.first());
               System.exit(0);
                }
               temp.delete(x.indexOf("MIN"),x.indexOf("(")+1);                  //finds minimum value when there is no condition
              temp.deleteCharAt(temp.indexOf(")"));
              flag=3;
            }
            
             str=temp.toString();
             
            s=new Scanner(str);
            ArrayList<Integer> sset=new ArrayList<Integer>();
            s.useDelimiter("  | ");
            while(s.hasNext())
            {
                String tst=s.next();
                if(tst.compareTo("SELECT")==0)
                sset=Select(s.next().trim());
            }
            s.close();
            ListIterator<Integer> LI=sset.listIterator();
            while(LI.hasNext())
            {
                if(flag==1)
                  System.out.print("UNIQ(");
                else if(flag==2)
                  System.out.print("MAX(");
            System.out.print(AL.get(LI.next()));                                //prints the sub headings
                if(flag==1||flag==2)
                  System.out.print(")"); 
            if(LI.hasNext())
                 System.out.print(",");
            else
                System.out.println();
            }
            
            
            
            TreeSet<Integer> wset;
           if(str.contains("WHERE"))
           {
                   
            wset=new TreeSet<Integer>();
            wset=Where(str.substring(str.indexOf("WHERE")+6).trim());
            Iterator<Integer> it=wset.iterator();
           
            
            if(flag==1)                                                         //to find the unique values
            {
              LinkedHashSet<String> LS=new LinkedHashSet<String>();
              Iterator<Integer> itr=wset.iterator();
              while(itr.hasNext())
              LS.add(m.get(AL.get(sset.get(0))).get(itr.next()));
              
              Iterator<String> itr2=LS.iterator();
              while(itr2.hasNext())
                  System.out.println(itr2.next());
              
              System.exit(0);
            }
            
             if(flag==2)                                                        //to find the maximum value
            {
             TreeSet<Double> LS=new TreeSet<Double>();
              Iterator<Integer> itr=wset.iterator();
              while(itr.hasNext())
              LS.add(Double.parseDouble(m.get(AL.get(sset.get(0))).get(itr.next())));
                System.out.println(LS.last());
                System.exit(0);
            }
             
              if(flag==3)                                                       //to find the minimum value
            {
             TreeSet<Double> LS=new TreeSet<Double>();
              Iterator<Integer> itr=wset.iterator();
              while(itr.hasNext())
              LS.add(Double.parseDouble(m.get(AL.get(sset.get(0))).get(itr.next())));
                System.out.println(LS.first());
                System.exit(0);
            }
             
            
            while(it.hasNext())                                                 //print values in other cases when there is no
            {                                                                   //MAX() , MIN() or UNIQ() function
                int key=it.next();
                LI=sset.listIterator();
                  while(LI.hasNext())
            {
            System.out.print(m.get(AL.get(LI.next())).get(key));   
            if(LI.hasNext())
                 System.out.print(",");
            else
                System.out.println();
            }
            }
           }
             
    }
}