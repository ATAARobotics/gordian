# The Gordian Specification

Gordian is very close to an object oriented language. It will evaluate every instruction as if it was a value. The only element that is not a value is blocks (if, while, etc.).

So,

    x = 25

is both a declaration of `x` and the value `25`.

Gordian is a dynamically typed interpreted scripting language with variables, methods, classes and scopes. It also has threading capabilities.

### Known Bugs

There are potential dangers when using keywords as variable names, method names and class names. Most of the time, you should be safe - but be weary about using language keywords where they might be misinterpreted.

# Values

Gordian has seven object types: Numbers, Booleans, Strings, Lists, Classes, Instances and Null.

Numbers are a combination of double (64 bit) and int (32 bit). If a value is `x % 1 == 0`, it is considered an integer internally. Otherwise, it is a double.

*Note: putting `.0` at the end of a number literal will not make it a double. This functionality is not built in to the language.* There is no need to build it in, because arithmetic will always use the most precise method.

Booleans are simple, you can use `true`, `True`, `false` or `False`.

Strings are indicated with single or double quotation marks at the very ends of the string. Escape characters are as follows:

`\"` = `"`

`\'` = `'`

`\t` = tab character

`\n` = newline character

Lists are declared using `[` and `]` around them, with values separated using commas.

Classes are defined by their name. Classes are actually objects, but can be constructed into operational instances using `new class(args)` notation.

Null is accessed using `null`.

# Adjustments

Booleans can also be adjusted using the basic operator.

`!` = Reverse a boolean (true -> false)

Numbers can be reversed using `-`, `neg(x)` or `x.neg()`.

To cast values to certain types, use the methods:

`int(x)` = Casts and convert to an integer (floors values & parses)

`num(x)` = Casts to a number (parses strings)

`bool(x)` = Casts to a boolean (parses strings)

`str(x)` = Casts to a string (parses all primitives)

# Variables

To declare variables, use the basic syntax:

    varName = value

Variable and method names must contain one letter, and cannot contain special characters (non-number or non-letter).

Variables have a scope, and cannot be accessed outside of their scope. When accessing a variable, just use the name of the variable as it was declared.

To declare variables in Java, use `GordianScope.variables().put("name", object)`.

To delete variables from memory, use `del x`.

# Methods

Methods are defined with the `def` keyword.

    def foo(x) {
        print(x + 1)
        return 13.23
    }

Arguments for methods will shadow other variables (will not delete them). 

Returning values is done with the `return` keyword.

To declare methods in Java, use `GordianScope.methods().put("name", method)`.

# Expressions

Expressions use the basic syntax used in most C-based languages.

`++` = Increment by 1

`--` = Decrement by 1

`&&` = Boolean AND

`||` = Boolean OR

`==` = Equals (works for all types)

`!=` = Not Equals (works for all types)

`>=` = Bigger or equal to

`<=` = Smaller or equal to

`>` = Bigger than

`<` = Smaller than

# Calculations

Calculations follow PMDMSA (Parentheses - Modulus - Division - Multiplication - Subtraction - Addition). Order or operations is changeable in `GordianScope.operations`.

Shorthand calculations are the same as Java/C++. They are just the symbol with an equals sign. (ex. `+=`)

# Blocks

Blocks are pieces of the program that have a private scope. This means that variables and methods defined in the block are only accessible inside of the block.

Example:

    def foo() {
        x = 13
    }
    
If blocks follow the basic syntax:

    if(condition) {
        # instructions
    } else if(condition) {
        # instructions
    } else {
        # instructions
    }

While blocks are similar

    while(condition) {
        # instructions
    }

For blocks run a certain amount of times

    for(3) {
        # instructions
    }

Count blocks perform a traditional for loop. The first argument is the variable name, the second is the start of the count and the third is the last (inclusive) count.

    count(x, 0, 10) {
        # instructions
    }

Thread blocks run in a new thread

    thread {
        # instructions
    }

Try blocks catch exceptions and move on (logging the exception)

    try {
        # instructions
    }

    # can also catch

    try {
        # instructions
    } catch {
        # exception handling
    }

Methods are defined using `def`

    def foo(x, i) {
        # instructions
    }

Class blocks create `ClassGenerator` instances under their name, and can be instantiated.

    class Foo {
        # instructions
    }

    # inherits Foo
    class Bar(Foo) {
        # instructions
    }

# Classes
Gordian uses special classes with some slightly unusual behaviour.

    class Example {
        x = 13

        def construct(x) {
            container.x = x
        }
    }

    myinstance = new Example(14)

To access methods and variables, simply use a `.` after the instance name.

    myinstance.x

    myinstance.foo(12)

Remember that `Example` is registered as a variable in Gordian. You should not shadow its value if you want to use the class.

Use inheritance like this:

    class Parent {
        x = 1
    }

    class Example(Parent) {
        def p() {
            print(x)
        }
    }

Calling `p()` on an instance of `Example` will print `1`.

# Libraries

**General**
- `container` - the scope that contains the current one, only exists when inside of a different scope
- `exit(x)` - exits the program with error code `x`
- `time()` - equivalent of Java's `System.currentTimeMillis()`
- `break()` - breaks from current scope (rest of instructions aren't run)
- `eval(x)` - runs a completely new script with no context
- `concat(i, x)` - concatenates two values
- `print(x)` - prints to console
- `sleep(x)` - sleeps for the specified number of milliseconds
- `rand()` - random double
- `randInt()` - random integer

**Booleans**

- `reverse()` - Reverses the boolean (true -> false, false -> true)

**Numbers**

- `neg()` - Reverses the number (negative -> postive, positive -> negative)
- `pow(x)` - To the power of `x`
- `sqrt()` - The square root of the number

**Strings**

- `charat(x)` - Character at the `x` index
- `indexof(x)` - Index of a string
- `length()` - Length of the string

**Lists**

- `add(x)` - Add `x` to the end of the list (appends)
- `add(i, x)` - Add `x` to the `i` index
- `addAll(x)` - Adds all values from another list
- `addAll(i, x)` - Adds all values from another list to `i` index
- `clear()` - Clears all elements from the list
- `contains(x)` - If the list contains `x`
- `containsAll(x)` - If the list contains all values in list `x`
- `get(x)` - Get value at the `x` index
- `indexOf(x)` - Get the index of object `x`
- `isEmpty()` - If list is completely empty
- `lastIndexOf(x)` - Get the last index of object `x`
- `remove(x)` - Removes the element at index `x` from the list
- `removeAll(x)` - Removes all elements in list `x` from the list
- `retainAll(x)` - Retains all elements in list `x` in the list
- `set(i, x)` - Sets the value at `i` index to `x`
- `size()` - Get the size of the list
- `sublist(s, e)` - A new list with elements from `s` to `e`
