import {
	Vector4
} from "../../build/three.module.js";


/** допоміжні методи */
var NURBSUtils = {

	/**
	 * Шукає проміжок вектора вузлів
	 *
	 * @param p - степінь
	 * @param u - параметричне значення
	 * @param U - вектор вузлів
	 *
	 * @return проміжок
	*/
	findSpan: function (p, u, U) {
		var n = U.length - p - 1;

		if ( u >= U[ n ] ) return n-1;
		if (u <= U[p]) return p;

		var low = p;
		var high = n;
		var mid = Math.floor((low+high)/2);

		while (u < U[mid] || u >= U[mid+1]){
			if (u < U[mid]) high = mid;
			else low = mid;

			mid = Math.floor((low+high)/2);
		}
		return mid;
	},


	/**
	 * Образовує базисну функцію за формулою
	 *
	 * @param span - проміжок в якому лежить @param u
	 * @param u - параметрична точка
	 * @param p - степінь
	 * @param U - вектор вузлів
	 *
	 * @return массив[р+1] з значеннями базисної функції
	*/
	calcBasisFunctions: function (span, u, p, U) {
		var N = [];
		var left = [];
		var right = [];
		N[0] = 1.0;

		for (var j = 1; j <= p; ++j) {
			left[j] = u - U[span+1-j];
			right[j] = U[span+j] - u;

			var saved = 0.0;

			for (var r = 0; r < j; ++r) {
				var rv = right[r+1];
				var lv = left[j-r];
				var temp = N[r] / (rv+lv);
				N[r] = saved + rv * temp;
				saved = lv * temp;
			 }
			 N[j] = saved;
		 }

		 return N;
	},

	/**
	 * Обраховує точку раціональної В-Сплайн площини
	 *
	 * @param p, q - ступені В-Спрайн поверхні
	 * @param U, V - вектори вузлів
	 * @param V
	 * @param P - контрольні точки (x, y, z, w)
	 * @param u, v - параметричні значення
	 *
	 * @return точку для заданих (u, v)
	*/
	calcSurfacePoint: function (p, q, U, V, P, u, v, target) {
		var uspan = this.findSpan(p, u, U);
		var vspan = this.findSpan(q, v, V);
		var Nu = this.calcBasisFunctions(uspan, u, p, U);
		var Nv = this.calcBasisFunctions(vspan, v, q, V);
		var temp = [];

		for (var l = 0; l <= q; ++l){
			temp[l] = new Vector4( 0, 0, 0, 0 );
			for (var k = 0; k <= p; ++ k){
				var point = P[uspan-p+k][vspan-q+l].clone();
				var w = point.w;
				point.x *= w;
				point.y *= w;
				point.z *= w;
				temp[l].add(point.multiplyScalar(Nu[k]));
			}
		}

		var Sw = new Vector4(0, 0, 0, 0);
		for (var l = 0; l <= q; ++ l){
			Sw.add(temp[l].multiplyScalar( Nv[ l ] ) );
		}

		Sw.divideScalar(Sw.w);
		target.set(Sw.x, Sw.y, Sw.z);
	}
};

export { NURBSUtils };
