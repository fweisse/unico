# unico
JAVA DEVELOPER (WEB)
Unico Programming Assignment:

The task:
Develop an enterprise Java application that implements RESTful and SOAP web services that is secure.

The RESTful service will expose two methods:
public String push(int i1, int i2);
which returns the status of the request to the caller as a String. The two parameters will be added to a JMS queue.
public List<Integer> list();
which returns a list of all the elements ever added to the queue from a database in the order added as a JSON structure. 

The SOAP service will expose the following method as operations:
public int gcd();
which returns the greatest common divisor* of the two integers at the head of the queue. These two elements will subsequently be discarded from the queue and the head replaced by the next two in line.
public List<Integer> gcdList();
which returns a list of all the computed greatest common divisors from a database. 
public int gcdSum();
which returns the sum of all computed greatest common divisors from a database.
