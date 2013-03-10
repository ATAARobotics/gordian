##Gordian##
===========

#Using Gordian
To run multiple methods / assignments in a row, separate the commands using semi-colons or newlines.

    foo();bar;
or

    foo()
    bar()
or

    foo();
    bar();

#Comments
Comments are marked using pound signs

    foo();
    #bar();
bar(); will never be run

##If statements
If statements use the notation

    if(statement) [
        foo();
    ]
There are no else statements. To check the opposite statement, use the notation

    if(!statement) [
        bar();
    ]

#While loops
While loops use the notation

    while(statement) [
        foo();
    ]

#For loops
For loops use the notation

    for(Integer) [
        bar();
    ]
For loops take the number of times to loop as an argument. They do not follow the typical notation.

#Assignments
To assign a variable use the notation

    string = "String"
    integer = 20
    double = 120.3443
    boolean = true
Type is inferred, so no type declaration is needed.

##Values
#Strings
Strings are in the notation
    "String"
Which is equivalent to 
    String
Quotation marks are ignored and treated as boundaries of the string (only if 2 quotation marks exist)
#Doubles / Integers
Double and Integer type is inferred (ie. 2 is an integer and 2.1 is a double), and cannot be declared.
#Calculations
All calculations are double values. Calculations follow basic PEDMAS rules. Parenthesis are not available (use multi-step calculations if needed).
Variables can be used in calculations.

    x = 10.3 / 1
    y = 2039 * 9 + x / 2
In this case

    x = (10.3 / 1.0)
    y = (2039 * 9.0) + ((10.3) / 2.0)
Integer division is treated as double division.

#Operators
Integers and Doubles can be incremented or decremented using "++" and "--"

    x = 10
    x--
    # x is 9
    x++
    # x is 10

#Booleans
Booleans can be literal values of "true" and "false".

    x = true
    y = false
They can also contain &&, ||, >, <, >=, <=, == and !=

    x = true && true
    # x is true
    x = x || false
    # x is still true
    x = 10 > 3
    # x is still true
    x = 12 < 7
    # x is false
    x = 1 >= 1
    # x is true
    x = 29 <= 2
    # x is false
    x = 12 == 12
    # x is true
    x = 13 != 13
    # x is false
