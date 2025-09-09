(setq a 10)
(func f (x) (prog (a) (setq a (plus x 1)) a))
(prog ()
  (setq r1 (f 5))
  (list r1 a))

  