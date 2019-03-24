using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DataFetcher : MonoBehaviour
{
    Vector3 index;
    // Start is called before the first frame update
    void Start()
    {

    }
 
    // Update is called once per frame
    void Update()
    {
        index = this.transform.Find("index").GetChild(0).position;
        Debug.Log(index.x * 100);
        //foreach (Transform child in this.transform)
        //{
        //}
    }
}
