(setq a 10)
(func f () (prog (a) (setq a 20) a))
(f)
a

(setq x 100)
(prog (x)
  (setq x 5)
  x)