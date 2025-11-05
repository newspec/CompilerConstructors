(cond true (plus 1 1) (plus 2 2))

(cond false (plus 1 1) (plus 2 2))

(cond (greater 5 3) 100 200)

(cond (less 5 3) 100 200)

(prog ()
  (setq x 5)
  (cond (greater x 0) (plus x 10) (minus x 10)))