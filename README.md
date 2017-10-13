# asciimath_editor
primitive asciimath editor, can export to latex 

## disclosure
Expect quite a few bugs.
I have deviated from the ascii math standard in some places according to my own preferences.

## purpose

With the asciimath editor you can type math quickly while maintaing readability!

asciimath code:
```
bf{1}


@to prove or disprove: @ E[A/B] = (E[A])/(E[B])

A: A sub ZZ
B: B sub ZZ\\ \{0\}
E[B] != 0

eq
E[A/B] &= sum_{a in A} sum_{b in B} a/b Pr[A=a, B = b] 
&= sum_{a in A} sum_{b in B} a/b Pr[A=a]*Pr[B = b]
endeq

(E[A])/(E[B]) = (sum_{a in A} a*Pr[A=a])/(sum_{b in B} b*Pr[B=b])

@the above equations are clearly not equal@

```

corresponding latex code:
```
\mathbf{1}\\

\text{to prove or disprove: }E\left[\frac{A}{B}\right]=\frac{E\left[A\right]}{E\left[B\right]}\\

A:A\subset \mathbb{Z}\\
B:B\subset \mathbb{Z}\backslash\{0\}\\
E\left[B\right]\ne 0\\

\begin{align} \\
E\left[\frac{A}{B}\right]&=\sum_{{a\in A}}\sum_{{b\in B}}\frac{a}{b}Pr\left[A=a,B=b\right]\\
&=\sum_{{a\in A}}\sum_{{b\in B}}\frac{a}{b}Pr\left[A=a\right]\cdot Pr\left[B=b\right]\\
\end{align} \\

\frac{E\left[A\right]}{E\left[B\right]}=\frac{\sum_{{a\in A}}a\cdot Pr\left[A=a\right]}{\sum_{{b\in B}}b\cdot Pr\left[B=b\right]}\\

\text{the above equations are clearly not equal}
```
*note this is MathJax Latex, not regular Latex. Conversion to regular Latex should be added soon

## TODO
- create an atom plugin with scalajs
- convert to regular Latex instead of MathJax Latex


