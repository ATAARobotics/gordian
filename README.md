# GORDIAN #
-----------

This is Gordian. It is a scripting language written in Java to run programs based solely on their text.
It is interpreted, and provides no guarantees. A lot of errors are caught at runtime, which might make your job harder. As a bonus however, Gordian is extremely lightweight, flexible and can be hacked very easily. (in a good way)


# Datatypes and Operators

### Numbers are doubles
    x = 12
    x = 232.32914

### Math works in floating point
    x = 12 / 23 ### => 0.5217391304347826

### Even integer math
    x = 2 / 1 ### => 2.0

### Enforce precedence by steps (Not parentheses!)
    x = 3 / 2 ### => 1.5
    x = x * 3 ### => 4.5
    x = x + 2 ### => 6.5

### Incrementing and decrementing
    x++ ### When x doesn't exist, starts at 0 (x++ is 1)
    x-- ### When x doesn't exist, starts at 0 (x-- is -1)

## Booleans
    x = true
    x = false
*Types are not bound to variables!

### Reverse booleans with !
    x = !true ### => false

### Equality (uses Object.equals() - strings too!)
    x = h == h ### => true

### Inequality
    x = h != g ### => true

### Comparisons
    x = 3 > 2 ### => true
    x = 4.5 < 2 ### => false
    x = 1 >= 1 ### => true
    x = 4 <= 10 ### => false
    
### Boolean operators
    x = true && false ### => false
    x = true || false ### => true

### Strings are created in quotes
    x = "Hello World" ### => Hello World
    
### Concatenate strings using `+`
    x = "Hello " + World

### Can also create strings without quotes, but removes spaces
    x = Hello World ### => HelloWorld

# Variables

### Type is not enforced
    x = true
    x = 3
    x = Hello

### Remove value using notation (sets as empty value)
    x = 

# Control flow
    x = 0

    if(x >= 1)
        x++
    else if(x < 0)
        x--
    else
        x = 12
    end

### For runs x amount of times
    for(x)
        x++
    end

    while(x++ < 100)
        ### Do things
    end

### Create methods with def
    def printMore(x)
        print(x + 3)
    end
    
### Returning methods use a `return(x)` method
    def get(x)
        return(x + 1)
    end
