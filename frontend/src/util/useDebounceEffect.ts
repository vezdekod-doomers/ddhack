import { useCallback, useEffect } from "react";

export const useDebouncedEffect = (start: any, effect: any, delay: any , deps: any) => {
  // eslint-disable-next-line react-hooks/exhaustive-deps
  const callback = useCallback(effect, deps);

  useEffect(() => {
    start();
    const handler = setTimeout(() => {
      callback();
    }, delay);

    return () => {
      clearTimeout(handler);
    };
  }, [callback, delay]);
}
