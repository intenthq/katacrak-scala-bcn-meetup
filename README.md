# Katacrack

## Instructions

For this Kata we will consider the following scenario.

We all are very aware of the differences in salary depending on the gender.
Because this is something that worries us, we've decided to present some data
that shows said differences.

As an input, we've requested the public administration with data about salaries.
They have provided us with a CSV file in this format:
- CSV separator is `,`
- Each line will represent a different person
- Columns present in the file: province,gender,salary
  - province: a string representing the province the person is from
  - gender: one of `M`, `F` or `O`, that stand for male, female and other
    non-binary gender identification
  - salary: positive integer number with the yearly gross salary

Our program will receive the file as an argument and will generate a summary
of the values in the standard output.

- The summary will contain the average salary for every gender and 
  the average salary for every gender in each province.
- The averages need to be rounded to the nearest integer.
- The output will be in the same CSV format as the input.
- The total summary needs to be the first one and contain `All`
  in the first column
- The rows need to be sorted by province (asc) and average salary (desc)


For example, when provided with an input file with this contents:
```
Barcelona,M,50000
Barcelona,F,20000
Barcelona,M,26000
Tarragona,F,30000
Girona,O,15000
```

The output should be the following:
```
All,M,38000
All,F,25000
All,O,15000
Barcelona,M,38000
Barcelona,F,20000
Tarragona,F,30000
Girona,O,15000
```

## How to run it

You can run it from the command shell with `$ sbt "run file"` or from the
sbt shell with `> run file`.

You can run the tests using the usual `sbt test`. One sample test is provided.
