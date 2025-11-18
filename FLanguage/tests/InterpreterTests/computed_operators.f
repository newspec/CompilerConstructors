((lambda (p) (cond (less p 0) minus plus)) -5 10 3)
((lambda (p) (cond (less p 0) minus plus)) 5 10 3)
(setq chooser (lambda (op)
  (cond (equal op 1) plus
    (cond (equal op 2) minus
    times))))
((chooser 1) 5 3)
((chooser 2) 5 3)
((chooser 3) 5 3)
((lambda (x) ((lambda (y) (lambda (z) (plus x (plus y z)))) 10)) 5 3)