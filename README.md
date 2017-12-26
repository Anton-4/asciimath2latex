# asciimath_parser
Parse multi-line ascii-math and export to a nice latex document.

## syntax
check http://asciimath.org/ to get an overview of all symbols

## disclosure
Expect some bugs.
I have deviated from the ascii-math standard in some places according to my own preferences.

## executing
navigate to scala folder, put input in `src/main/resources/input.fastm`,
output will be in `output.tex`, run with `sbt run`.

## purpose

With ascii-math you can type math quickly while maintaining readability!

ascii-math code:
```
### 1

to prove or disprove: $E[A/B] = (E[A])/(E[B])$

eq
A: A sub ZZ
B: B sub ZZ\\ \{0\}
E[B] != 0
endeq

eq
E[A/B] &= sum_{a in A} sum_{b in B} a/b Pr[A=a, B = b]
&= sum_{a in A} sum_{b in B} a/b Pr[A=a]*Pr[B = b]
endeq

eq
(E[A])/(E[B]) = (sum_{a in A} a*Pr[A=a])/(sum_{b in B} b*Pr[B=b])
endeq

the above equations are clearly not equal

```

corresponding latex code:
```
\subsubsection{  1 }

to prove or disprove: $E\left[\frac{A}{B}\right]=\frac{E\left[A\right]}{E\left[B\right]}$

\begin{align*}
A:A\subset \mathbb{Z}\\
B:B\subset \mathbb{Z}\backslash\{0\}\\
E\left[B\right]\ne 0
\end{align*}

\begin{align*}
E\left[\frac{A}{B}\right]&=\sum_{{a\in A}}\sum_{{b\in B}}\frac{a}{b}Pr\left[A=a,B=b\right]\\
&=\sum_{{a\in A}}\sum_{{b\in B}}\frac{a}{b}Pr\left[A=a\right]\cdot Pr\left[B=b\right]
\end{align*}

\begin{align*}
\frac{E\left[A\right]}{E\left[B\right]}=\frac{\sum_{{a\in A}}a\cdot Pr\left[A=a\right]}{\sum_{{b\in B}}b\cdot Pr\left[B=b\right]}
\end{align*}

the above equations are clearly not equal
```
