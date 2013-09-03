# The Gordian Specification

Gordian is what could be refered to as a *value-oriented* language. Like an object oriented laguage, Gordian treats everything like a value. It will evaluate every instruction as if it was a value. The only element that is not a value is blocks (if, while, etc.) - which are composed of values.

So,

    x = 25

is both a declaration of `x` and the value `25`.

Gordian is a dynamically typed interpreted scripting language with variables, methods and scopes. It also has threading capabilities.

# Values

Gordian has four value types. Numbers, Booleans, Strings and Null.

Numbers are a combination of double (64 bit) and int (32 bit). If a value is `x % 1 == 0`, it is considered an integer internally. Otherwise, it is a double.

*Note: putting `.0` at the end of a number literal will **not** make it a double. This functionality is not built in to the language.* There is no need to build it in, because arithmetic will always use the most precise method.

Booleans are simple, they are the same as in java. You can use `true` or `false`, with any or all letters capitalized.

Strings are indicated with single or double quotation marks at the very ends of the string. Escape characters are as follows:

`\"` = `"`

`\t` = tab character

`\n` = newline character

Null is accessed using the keyword `null`.

Values can also be adjusted using basic operators.

`!` = Reverse a boolean (true -> false)

`-` = Make number negative (1 -> -1)

`+` = Make number positive (1 -> 1)

To cast values to certain types, use the methods:

`int(x)` = Casts and convert to an integer (floors values & parses)

`num(x)` = Casts to a number (parses strings)

`bool(x)` = Casts to a boolean (parses strings)

`str(x)` = Casts to a string (converts numbers and booleans)

# Variables

To declare variables, use the basic syntax:

    varName = value

Variable names cannot contain these characters:

    =, :, +, -, *, /, >, <, &, |, !, (, ), ", '

Variables have a scope, and cannot be accessed outside of their scope. When accessing a variable, just use the name of the variable as it was declared.

To declare variables in Java, use `Gordian.addVariable()`.

# Methods

Methods are defined with the `def` keyword.

    def foo(x):
        print(x + 1);
    fi

End all blocks using `fi`.

Arguments for methods will shadow other variables (will not delete them). They are not available outside of the method's scope.

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

Calculations follow MDMSA (Modulus - Division - Multiplication - Subtraction - Addition). Order or operations is changeable in `GordianRuntime.operations`. Parentheses are **not supported**. (for now)

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

Methods are defined using `def`

    def foo(x, i):
        # instructions
    fi

Scope blocks perform no additional actions, but they encapsulate the variables and methods stored within them.

    scope:
        # instructions
    fi
