# GORDIAN #
===========
Lightweight portable scripting language interpreted and run inside of Java with minimal dependencies.

## Purpose
Gordian is designed specifically for [FRC](http://en.wikipedia.org/wiki/FIRST_Robotics_Competition) programming. Fortunately, it is useful for all kinds of different functions.

Gordian is made to be an easy-to-learn scripting language meant for simple programs with normal control flow. It does not natively support threading or objects, but it can be engineered to simulate them.

Gordian is well suited for:

- Very small linear scripts
- Complex control flow with input and output
- Interpreter of input (ie. configuration files)

Gordian is modelled with inspiration from Python and Lua.

## Platform
Gordian is designed to run on a minimum of Java ME 1.4 on the squawk VM, using the WPILibJ libraries.

- Gordian for FRC is available at https://github.com/Team4334/gordian
- Gordian for Java SE (or your choice of supported platform) is available at https://github.com/Team4334/gordianSE

## Project Goals
Gordian is a project that aims to help innovate quickly without tinkering embedded code. Its goals are simple:

- Provide easy, safe syntax for any use
- Avoid any feature creep and stay stable

## Calling from Java
Use `Gordian.run` to run your script in Java. For more access to variables and elements, create a `Scope` object in the same way as `Gordian.run`, and call `Scope.run(String)` to run your script. The `Scope` object has access to some useful internals of the language.

# Language specification
Gordian follows a semi-strict, dynamic syntax that allows the user many options for scripts. Its syntax is designed to avoid as many possible parsing bugs and unpredictable behaviour.

# Basics
Gordian separates instructions using line breaks (\n) and semi-colons (;). They are functionally equivalent. For this reason, you can use this notation:

    def foo(x)
        # instructions
    end

Or this notation:

    def foo(x); # instructions; end
    
They are the same to Gordian.

## Data
There are three main data types in Gordian - Numbers, Booleans and Strings. Each is declared as follows

    # Number
    13.23233
    11
    
    # Boolean
    true
    false

    # String
    "Hello World"
    ""

Unless you work behind the scenes, these are the only data types you will use.

### Adjustments
To reverse a boolean, insert a `!` in front of it.

    !true # is false
    !false # is true
    !!!true # is false
    
To make a number negative, insert a `-` in front of it.

    -3 # is -3
    --2 # is 2

### Calculations
Calculations follow MDASM

> Multiplication
> Division
> Addition
> Subtraction
> Modulus
> Left > Right

Parentheses **are not supported**. Enforce order with multiple steps!

    3  * 20 + 2.3 / 1 # is (3 * 20) + (2.3 / 1)
    5 / 6 / 4 # is (5 / 6) / 4

### Expressions
Expressions evaluate two elements.

    # `==` and `!=` work on strings and numbers
    "H" == "H" # is true
    13 != 12.99 # is true
    
    # `+` concatenates two strings together
    "H" + "e" + "l" + "l" + "o" # is Hello
    
    # `>`, `<`, `>=`, `<=` evaluate numbers
    13 > 12 # is true
    12.5 < 2 # is false
    10 >= 10 # is true
    32 <= 15 # is false
    
    # `&&` and `||` evaluate booleans
    true && false # is false
    true || false # is true

### Literals
Literals are direct representations of data written in code.

**Numbers**

Numbers are inferred between floating point and integer. If floating point is needed (`x % 1 != 0`), the number will be represented as floating point. Otherwise it will be an integer.

You shouldn't worry about differences between floating point and integer. If you need flooring or ceiling arithmetic, create the functions yourself.

    3 # is functionally equivalent to 3.0
    3.0 # is functionally equivalent to 3
    
**Booleans**

Boolean literal values are `true` or `false`. Case is ignored (so `True` is still true).

**Strings**

Strings are surrounded with quotation marks. Everything in between the quotation marks is regarded as part of the string.

Quotation marks within quotation marks are part of the string, but can remove spaces from the string.

    "Hello " World" # is Hello "World
    "Hello "" World" # is Hello "" World
    
All spaces after odd (1st, 3rd, 5th, etc.) quotation marks are removed.

## Variables
Variables store data under a key that can be accessed by the program. Variables have no type, meaning you can easily do this:

    x = 23
    x = false
    
This code causes no errors.

### Declaration
To declare a variable, use the equals sign. Type is inferred, and the value is interpreted in the current scope of the program.

    x = "Hello World"

### Creation / Deletion
To create an "empty" value (a plain java `Object` with no discernible qualities), use the `make` keyword.

    make x
    
This reserves a space in the scope's memory, allowing you to later declare the variable and preserve its scope.

To delete a known variable, use the `del` keyword. This will only delete the *most local* version of the variable, so if two `x` existed (one being shadowed), only the more local one would be removed.

## Methods
You can create `UserMethod` objects and add them in `Gordian.run`, and/or define methods inside of your script.

### Java
Submit `UserMethod` objects in `Gordian.run` or `Scope` constructor. Here's an example print method

    new UserMethod("print") {
        public void run(Value[] arguments) {
            System.out.println(arguments[0].getValue());
        }
    }

### Defined Methods
Create methods in your code using the `def` keyword.

    def foo(x)
        # business code here
        # can access x
    end
    
Remember that you cannot call a defined method until the method is defined. Define the method before using it!

#### Returning
Use the `return` keyword to return a value from a defined method.

    def spitBack(x)
        return x
    end

You can treat `spitBack(x)` as a value now.

## Control flow
All blocks are completed with the `end` keyword.

### If
If runs if the condition is true

    if(1 == 1)
        # business code here
    else if (2 != 1)
        # business code here
    else
        # business code here
    end

### For
For loops over instructions for an amount of times. The number is evaluated only once, when starting the loop.

    for (5)
        # business code here
    end
    
### While
While loops until its argument is false

    x  = 0
    while(x < 100)
        x++
    end

## Scope
Every method, variable and returning method has a scope. It is only accessible inside of that scope.

    x = 0
    
    def foo(x)
        # can't access original x, only argument x
        i = 12
        if(true)
            y = 13
            
            def foo2()
            end
        end
        # can't access y, foo2()
    end
    
    # can't access i, y, foo2()
