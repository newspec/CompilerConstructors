(and (isint 5) (isreal 5.0))

(isnull null)

(prog ()
  (setq a '(1 2))
  (and (islist a) (not (isatom a))))
  
(isbool false)
