(func Zero () 0)
(Zero)

(setq f (lambda (x) (plus x 1)))
(f 4)

(func chooser (p) (cond (less p 0) (plus 2 3) minus))
(setq g (chooser 2))
(g 10 3)

(prog ()
  (setq a 1)
  (plus (setq a (plus a 1)) a))
