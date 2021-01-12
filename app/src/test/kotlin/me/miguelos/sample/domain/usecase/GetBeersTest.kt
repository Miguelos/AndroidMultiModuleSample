package me.miguelos.sample.domain.usecase

import io.reactivex.rxjava3.internal.operators.completable.CompletableEmpty
import io.reactivex.rxjava3.internal.operators.completable.CompletableError
import junit.framework.TestCase
import me.miguelos.sample.domain.PunkRepository
import me.miguelos.base.interactors.ExecutionSchedulers
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


class GetBeersTest : TestCase() {

    @Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var punkRepository: PunkRepository

    @Mock
    private lateinit var executionSchedulers: ExecutionSchedulers

    @InjectMocks
    private lateinit var getBeers: GetBeers

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testValidate() {
        assertThat(
            getBeers.validate(
                GetBeers.RequestValues(false, "", 0, 10, false)
            ),
            instanceOf(CompletableEmpty.INSTANCE::class.java)
        )
    }

    @Test
    fun testValidateError() {
        assertThat(
            getBeers.validate(null),
            instanceOf(CompletableError::class.java)
        )
    }
}
