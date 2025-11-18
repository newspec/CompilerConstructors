(func add (a b)
  (plus a b))
(add 3 4)

(func double (x)
  (times x 2))
(double 5)

(func const ()
  42)
(const)

(func quad (x)
  (times (double x) (double x)))
(quad 3)
