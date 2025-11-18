((lambda (p) (cond (less p 0) plus minus)) 1 10 5)
(setq op (lambda (x) (cond (greater x 0) times divide)))
((op 5) 20 4)
((op -1) 20 4)