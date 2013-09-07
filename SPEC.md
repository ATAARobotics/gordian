# The Gordian Specification

Gordian is what could be refered to as a *value-oriented* language. Like an object oriented laguage, Gordian treats everything like a value. It will evaluate every instruction as if it was a value. The only element that is not a value is blocks (if, while, etc.) - which are composed of values.

So,

    x = 25

is both a declaration of `x` and the value `25`.

Gordian is a dynamically typed interpreted scripting language with variables, methods and scopes. It also has threading capabilities.

# Values

Gordian has four value types: Numbers, Booleans, Strings and Null.

Numbers are a combination of double (64 bit) and int (32 bit). If a value is `x % 1 == 0`, it is considered an integer internally. Otherwise, it is a double.

*Note: putting `.0` at the end of a number literal will not make it a double. This functionality is not built in to the language.* There is no need to build it in, because arithmetic will always use the most precise method.

Booleans are simple, you can use `true` or `false`, with any or all letters capitalized.

Strings are indicated with single or double quotation marks at the very ends of the string. Escape characters are as follows:

`\"` = `"`

`\t` = tab character

`\n` = newline character

Null is accessed using the variable `null`.

Booleans can also be adjusted using the basic operator.

`!` = Reverse a boolean (true -> false)

Numbers can be reversed using `neg(x)`.

To cast values to certain types, use the methods:

`int(x)` = Casts and convert to an integer (floors values & parses)

`num(x)` = Casts to a number (parses strings)

`bool(x)` = Casts to a boolean (parses strings)

`str(x)` = Casts to a string (converts numbers and booleans)

# Variables

To declare variables, use the basic syntax:

    varName = value

Variable and method names cannot contain characters that are not letters.

Variables have a scope, and cannot be accessed outside of their scope. When accessing a variable, just use the name of the variable as it was declared.

To declare variables in Java, use `Gordian.addVariable()`.

To delete variables from memory, use `delete(x)`. `x` has to be a string.

# Methods

Methods are defined with the `def` keyword.

    def foo(x):
        print(x + 1)
        return(13.23)
    fi

End all blocks using `fi`.

Arguments for methods will shadow other variables (will not delete them). They are not available outside of the method's scope.

Returning values is done with the `return(x)` method.

To declare methods in Java, use `Gordian.addMethod()`.

# Expressions

Expressions use the basic syntax used in most C-based languages.

`++` = Increment by 1

`--` = Decrement by 1

`&&` = Boolean AND

`||` = Boolean OR

`==` = Equals (works for all types)

`!=` = Not Equals

`>=` = Bigger or equal to

`<=` = Smaller or equal to

`>` = Bigger than

`<` = Smaller than

Strings can be concatenated using the `+` sign.

# Calculations

Calculations follow PMDMSA (Parentheses - Modulus - Division - Multiplication - Subtraction - Addition). Order or operations is changeable in `GordianRuntime.operations`.

Shorthand calculations are the same as Java/C++. They are just the symbol with an equals sign. (ex. `+=`)

# Blocks

Blocks are pieces of the program that have a private scope. This means that variables and methods defined in the block are only accessible inside of the block.

Example:

    def foo():
        x = 13
    fi
    
    # CANNOT ACCESS `x`

If blocks follow the basic syntax:

    if(condition):
        # instructions
    fi

While blocks are similar

    while(condition):
        # instructions
    fi

For blocks run a certain amount of times

    for(3):
        # instructions
    fi

Count blocks perform a traditional for loop. The first argument is the variable name, the second is the start of the count and the third is the last (inclusive) count.

    count(x, 0, 10):
        # instructions
    fi

Thread blocks run in a new thread

    thread:
        # instructions
    fi

Try blocks catch exceptions and move on (logging the exception)

    try:
        # instructions
    fi

Methods are defined using `def`

    def foo(x, i):
        # instructions
    fi

Scope blocks perform no additional actions, but they encapsulate the variables and methods stored within them.

    scope:
        # instructions
    fi

Class blocks create `ClassGenerator` instances under their name, and can be instantiated.

    class Foo:
        # instructions
    fi

# Classes
Gordian uses a scraped down version of typical object oriented languages. Classes do not have inheritence, so many object-oriented features are not available. To construct an object, use the `[` and `]` signs.

    class Example:
        x = 13

        def foo(x):
            super.x = x
        fi
    fi

    myinstance = [Example]

There cannot be arguments in construction. As you can tell, you can access variables that are shadowed using the `super` keyword. Super will work from any context in Gordian (provided you are in a scope). To access methods and variables, simply use a `.` after the instance name.

    myinstance.x

    myinstance.foo(12)

Remember that `Example` is registered as a variable in Gordian. You should not shadow its value if you want to use the class.

Nested classes clone all values and methods from their external scope. Changing any value inside of a class *only* affects it inside of that class.
