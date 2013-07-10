# GORDIAN #
-----------

This is Gordian. It is a scripting language written in Java to run programs based solely on their text.
It is interpreted, and provides no guarantees. A lot of errors are caught at runtime, which might make your job harder. As a bonus however, Gordian is extremely lightweight, flexible and can be hacked very easily. (in a good way)
It isn't meant to solve any problem that another language couldn't. It's purpose is to be run on robots (see [FRC](https://en.wikipedia.org/wiki/FIRST_Robotics_Competition)). Since implementing another language would require a lot of dependencies that I just can't do on the platform, I wanted to make the language portable and by itself using only parsing.
And the platform I run it on is Java ME with [squawk](http://en.wikipedia.org/wiki/Squawk_virtual_machine).

# Basics
Gordian separates instructions using line breaks (\n) and ';'. They are functionally equivalent. For this reason, you can use this notation:

    def foo(x)
        # instructions
    end

Or this notation:

    def foo(x); # instructions; end
    
They are the same to Gordian.

## Calling from Java
Use `Gordian.run` to run your script in Java. For more access to variables and elements, create a `Scope` object in the same way as `Gordian.run`, and call `Scope.run(String)` to run your script. The `Scope` object has access to some useful internals of the language.

# Datatypes and Operators

### Numbers are int/double variants - in Java they are GordianNumber
    x = 2.0 ### => 2
    x = 3.42 ### => 3.42
    
### Math works in floating point
    x = 12 / 23 ### => 0.5217391304347826

### But results are inferred type
    x = 2 / 1 ### => 2

### Enforce precedence by steps (Not parentheses!)
    x = 3 / 2 ### => 1.5
    x = x * 3 ### => 4.5
    x = x + 2 ### => 6.5
    
### Modulus > Multiplication > Division > Addition > Subtraction > Right > Left
    x = 3 * 2 * 1 ### => (3 * (2 * 1))
    x = 1 / 2 / 3 ### => (1 / (2 / 3))
    x = 1 * 2 / 3 + 4 - 5 ### => (((1 * (2 / 3)) + 4) - 5)

### Incrementing and decrementing
    x++ ### When x doesn't exist (or isn't a number), starts at 0 (x++ is 1)
    x-- ### When x doesn't exist (or isn't a number), starts at 0 (x-- is -1)
    
### Declarations and increments are both considered values as well
    foo(x++) ### Is correct
    foo(x = x + 1) ### Is also correct

# Booleans
    x = true
    x = false
    
Types are not bound to variables!

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

### Create empty variables using `make`
    make varName ### => Empty string value

### Remove value using `del`
    del x ### => Var no longer exists
    
### Type is not enforced
    x = true
    x = 3
    x = Hello

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
    
### Returning methods use a `return x`
    def get(x)
        return x + 1
    end

### Everything has a scope
    def foo()
        def bar()
        end
        
        ### Can call bar!
    end
    
    ### Can't call bar!

# Language quirks

- Variable shadowing is not supported
- Parentheses are not supported for any operation
- Using the `x = val` notation as a value is equivalent to using `val`, and calling `x = val`. It has nothing to do with the method's variables (like python).
