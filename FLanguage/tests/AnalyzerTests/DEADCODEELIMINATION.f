(prog ()
  (setq x 5)
  (return 10)
  (plus x 20))

(func f ()
  (prog ()
    (setq y 3)
    (return y)
    (setq z 100)))

(prog ()
  (setq x 5)
  x)