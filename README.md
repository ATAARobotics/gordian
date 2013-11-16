# GORDIAN #
===========
Lightweight portable scripting language interpreted and run inside of Java with minimal dependencies.

## Purpose
Gordian is designed specifically for [FRC](http://en.wikipedia.org/wiki/FIRST_Robotics_Competition) programming. Fortunately, it is useful for all kinds of different functions.

Gordian is made to be an easy-to-learn scripting language meant for simple programs with normal control flow. 

Gordian is well suited for:

- Function-based scripts
- Complex control flow with input and output
- Interpreter of input (ie. configuration files)

Gordian is modelled with inspiration from Java, Python and Lua.

## Platform
Gordian is designed to run on Java ME 1.4 on the squawk VM, using the WPILibJ libraries.

- Gordian for FRC is available at https://github.com/Team4334/gordian
- Gordian for Java SE (or your choice of supported platform) is available at https://github.com/Team4334/gordianSE
- Gordian as a desktop program is available at https://github.com/Team4334/desktop-gordian

## Project Goals
Gordian is a project that aims to help innovate quickly without tinkering embedded code. Its goals are simple:

- Provide easy, safe syntax for any use
- Avoid any feature creep and stay stable

## Calling from Java
Use `GordianScope` object to run your script in Java.

# Language specification
Gordian follows a semi-strict, dynamic syntax that allows the user many options for scripts. Its syntax is designed to avoid as many possible parsing bugs and unpredictable behaviour.

Full specification is available [here](https://github.com/Team4334/gordian/blob/master/SPEC.md).
