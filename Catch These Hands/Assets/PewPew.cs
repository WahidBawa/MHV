using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PewPew : MonoBehaviour
{
    // Start is called before the first frame update
    public double dist;
    public Vector3 rightHand;
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        rightHand = this.transform.Find("palm").position;
        dist = (this.transform.Find("index").Find("bone3").position.z - this.transform.Find("palm").position.z) * 100;

    }
}
