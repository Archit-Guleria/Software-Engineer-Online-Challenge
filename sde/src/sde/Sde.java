package sde;
import java.util.*;
import java.io.*;
public class Sde 
{
    static ArrayList<String> AL=new ArrayList<String>();
    static LinkedHashMap<String,LinkedList<String>> m=new LinkedHashMap<String,LinkedList<String>>();
    static LinkedList<String> arr[];
    
   
static String max(String str)                                                   //Calculates maximum value of an attribute 
{
    return Collections.max(m.get(str));
}




static ArrayList<Integer> Select(String str)
{
     LinkedList<String> aLs=new LinkedList<String>(); 
     ArrayList<Integer> L=new ArrayList<Integer>();
     
     if(str.compareTo("*")==0)
     {
         aLs.addAll(AL);
         
     }
     else
     {
     Scanner sc=new Scanner(str);
     sc.useDelimiter(",");
     while(sc.hasNext())
     aLs.add(sc.next());
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
    

    static TreeSet<Integer> solve(String str)
    {
    Scanner sc=new Scanner(str);
    sc.useDelimiter(">=|<=|=|>|<");
    TreeSet<Integer> set=new TreeSet<Integer>();
    String g=sc.next().trim();
    String comp=sc.next().trim();
    String op=str.substring(str.indexOf(g)+g.length(),str.indexOf(comp)).trim();
    int index=0;
    TreeSet<Integer> se;
    switch(op)
    {
        case ">":
            index=0;
            se=new TreeSet<Integer>();
             while(index<m.get(g).size())
             {
                 if( Double.parseDouble(m.get(g).get(index)) > Double.parseDouble(comp))
                 {
                     se.add(index);
                     index++;
                 }
            else index++;
             }
            set.addAll(se);
            se.clear();
            break;
            
        case "<": 
            index=0;
            se=new TreeSet<Integer>();
             while(index<m.get(g).size())
             {
                 if( Double.parseDouble(m.get(g).get(index)) < Double.parseDouble(comp))
                 {
                     se.add(index);
                     index++;
                 }
            else index++;
             }
            set.addAll(se);
            se.clear();
            break;
            
        case "=":
            index=0;
            se=new TreeSet<Integer>();
             while(index<m.get(g).size())
             {
                 if( m.get(g).get(index).trim().compareToIgnoreCase(comp) == 0)
                 {
                     se.add(index);
                     index++;
                 }
            else index++;
             }
            set.addAll(se);
            se.clear();
            break;
            
        case ">=":
             index=0;
            se=new TreeSet<Integer>();
             while(index<m.get(g).size())
             {
                 if( Double.parseDouble(m.get(g).get(index)) > Double.parseDouble(comp)||Double.parseDouble(m.get(g).get(index)) == Double.parseDouble(comp))
                 {
                     se.add(index);
                     index++;
                 }
            else index++;
             }
            set.addAll(se);
            se.clear();
            break;
            
        case "<=":
             index=0;
            se=new TreeSet<Integer>();
             while(index<m.get(g).size())
             {
                 if( Double.parseDouble(m.get(g).get(index)) < Double.parseDouble(comp)||Double.parseDouble(m.get(g).get(index)) == Double.parseDouble(comp))
                 {
                     se.add(index);
                     index++;
                 }
            else index++;
             }
            set.addAll(se);
            se.clear();
            break;
    }
    return set;
    }

    static TreeSet<Integer> Where(String str)
    {
    Scanner sc=new Scanner(str);
    TreeSet<Integer> set1=new TreeSet<Integer>();
    TreeSet<Integer> set2=new TreeSet<Integer>();
    
    if(str.contains("AND")||str.contains("OR"))
    {
        if(str.contains("("))
            set2.addAll(Where(str.substring(str.indexOf("(")+1,str.indexOf(")")).trim()));
        else if(str.contains("AND") && !str.contains("OR"))
            set2.addAll(solve(str.substring(str.lastIndexOf("AND")+4)));
        else if(!str.contains("AND") && str.contains("OR"))
            set2.addAll(solve(str.substring(str.lastIndexOf("OR")+3)));
                
        String j=null;
        if(str.contains("AND") && str.contains("OR"))
        {
            if(str.indexOf("AND")<str.indexOf("OR"))
                 j="AND";
            else
                 j="OR";
            set1.addAll(solve(str.substring(0,str.indexOf(j)-1)));
           if(j.compareTo("OR")==0)
               set1.addAll(set2);
               else
                set1.retainAll(set2);
        }
        else if(str.contains("AND"))
        {
            set1.addAll(solve(str.substring(0,str.indexOf("AND")-1)));
             set1.retainAll(set2);
        }
        else if(str.contains("OR"))
        {
              set1.addAll(solve(str.substring(0,str.indexOf("OR")-1)));
               set1.addAll(set2);
        }
    }
    else
    set1.addAll(solve(str));  
    return set1;
    }
          
    
    

    
    public static void main(String[] args) throws IOException{                   //main function
        FileReader  r=new FileReader("sql_engine_dataset.csv");
        Scanner s=new Scanner(r);
        String line="";
        int count_headings=0;
        
        
        
        if(s.hasNextLine())
            line=s.next();
        else 
        System.out.println("Titles missing");
        
        
        
        Scanner scan=new Scanner(line);
        scan.useDelimiter(",");
        while(scan.hasNext())
        {
            count_headings++;
            m.put(scan.next(),null);
        }
        
        
        
       arr=new LinkedList[count_headings];
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
                   arr[count].add(scan.next());
                   count++;
               }
           }
               else
               break;
        }
             
         
              int count=0;
               for(Map.Entry<String,LinkedList<String>> me:m.entrySet()){
                   m.put(me.getKey(),arr[count]);
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
              temp.delete(x.indexOf("UNIQ"),x.indexOf("(")+1); 
              temp.deleteCharAt(temp.indexOf(")"));
              flag=1;
            }
            str=temp.toString();
          
            System.out.println();
            
            if(str.contains("MAX"))
            {
               String res= str.substring(str.indexOf('(')+1,str.indexOf(')')).trim();
               System.out.println("MAX("+res+")");  
               System.out.println(max(res));
               System.exit(0);
            }
            
            s=new Scanner(str);
            ArrayList<Integer> sset=new ArrayList<Integer>();
            s.useDelimiter("  | ");
            while(s.hasNext())
            {
                String tst=s.next();
                if(tst.compareTo("SELECT")==0)
                sset=Select(s.next().trim());
            }
            ListIterator<Integer> LI=sset.listIterator();
            while(LI.hasNext())
            {
                if(flag==1)
                  System.out.print("UNIQ(");  
            System.out.print(AL.get(LI.next())); 
                if(flag==1)
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
            
            if(flag==1)
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
            
            
            while(it.hasNext())
            {
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
