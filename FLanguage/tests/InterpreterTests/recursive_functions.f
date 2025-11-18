(func factorial (n)
  (cond (equal n 0) 1
    (times n (factorial (minus n 1)))))
(factorial 5)
(func sum (n)
  (cond (equal n 0) 0
    (plus n (sum (minus n 1)))))
(sum 5)
(func fib (n)
  (cond (less n 2) n
    (plus (fib (minus n 1)) (fib (minus n 2)))))
(fib 6)
