# The Gordian Specification

Gordian is what could be refered to as a *value-oriented* language. Like an object oriented laguage, Gordian treats everything like a value. It will evaluate every instruction as if it was a value. The only element that is not a value is blocks (if, while, etc.) - which are composed of values.

So,

    x = 25

is both a declaration of `x` and the value `25`.

Gordian is a dynamically typed interpreted scripting language with variables, methods, classes and scopes. It also has threading capabilities.

# Values

Gordian has seven value types: Numbers, Booleans, Strings, Lists, Classes, Instances and Null.

Numbers are a combination of double (64 bit) and int (32 bit). If a value is `x % 1 == 0`, it is considered an integer internally. Otherwise, it is a double.

*Note: putting `.0` at the end of a number literal will not make it a double. This functionality is not built in to the language.* There is no need to build it in, because arithmetic will always use the most precise method.

Booleans are simple, you can use `true` or `false`, with any or all letters capitalized.

Strings are indicated with single or double quotation marks at the very ends of the string. Escape characters are as follows:

`\"` = `"`

`\t` = tab character

`\n` = newline character

Lists are declared using `{` and `}` around them, with values separated using commas.

Classes are defined by their name. Classes are actually values, but can be constructed into operational instances using `[class]` notation.

Null is accessed using the variable `null`.

# Adjustments

Booleans can also be adjusted using the basic operator.

`!` = Reverse a boolean (true -> false)

Numbers can be reversed using `neg(x)` or `x.neg()`. This is preferred to using `-` before numbers (parsing is not predictable).

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

Classes clone all values and methods from their external scope. Changing any value inside of a class *only* affects it inside of that class.

# Libraries
**Booleans**

`reverse()` - Reverses the boolean (true -> false, false -> true)

**Numbers**

`neg()` - Reverses the number (negative -> postive, positive -> negative)
`pow(x)` - To the power of `x`
`sqrt()` - The square root of the number

**Strings**

`charat(x)` - Character at the `x` index
`indexof(x)` - Index of a string
`length()` - Length of the string

**Lists**

`get(x)` - Get value at the `x` index
`add(x)` - Add `x` to the end of the list (appends)
`addAll(x)` - Adds all values from another list
`set(i, x)` - Sets the value at `i` index to `x`
`clear()` - Clears all elements from the list
`remove(x)` - Removes the element `x` from the list
`removeat(x)` - Removes the element at `x` index
`size()` - Get the size of the list
`contains(x)` - If the list contains `x`
